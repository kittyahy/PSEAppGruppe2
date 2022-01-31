/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/
package com.pseandroid2.dailydataserver.onlineDatabase;

import com.pseandroid2.dailydataserver.onlineDatabase.DeltaDB.Delta;
import com.pseandroid2.dailydataserver.onlineDatabase.DeltaDB.DeltaRepository;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectParticipant;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectParticipantID;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectParticipantsRepository;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectRepository;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeltaOrganisationService {

    private static final long MAX_POSTS_PER_DAY = 128;
    private static final int MAX_COMMAND_SIZE = 1000;
    private static final long REMOVE_TIME_DAYS = 3;

    @Autowired
    private final DeltaRepository deltaRepo;
    @Autowired
    private final ProjectRepository projectRepo;
    @Autowired
    private final FetchRequestService fetchRequestService;
    @Autowired
    private final ProjectParticipantsRepository ppRepo;


    public DeltaOrganisationService(DeltaRepository deltaRepo, FetchRequestService fetchRequestService, ProjectParticipantsRepository ppRepo, ProjectRepository projectRepo) {
        this.deltaRepo = deltaRepo;
        this.fetchRequestService = fetchRequestService;
        this.ppRepo = ppRepo;
        this.projectRepo = projectRepo;
    }

    //#TODO Kann nicht: downloadedBy
    public boolean saveDelta(long projectID, String user, String projectCommand) {
        removeOutDatedDeltas();
        long amountOfPosts = deltaRepo.countByUserAndAddedToServerIsAfter(user, LocalDateTime.now().minusDays(1));
        if (amountOfPosts >= MAX_POSTS_PER_DAY) { //Amount of Post per Day and per User
            return false;
        }
        System.out.println(projectCommand);

        //size of the command
        if (projectCommand.getBytes().length > MAX_COMMAND_SIZE) {
            return false;
        }
        ProjectParticipant participant = ppRepo.findById(new ProjectParticipantID(user, projectID)).get();
        Delta delta = new Delta(user, projectCommand, projectID, (participant.getRole() == Role.ADMIN));
        deltaRepo.save(delta);
        projectRepo.findById(projectID).get().setLastUpdated(LocalDateTime.now());
        return true;

    }

    // #TODO Kann nicht: Kontrollieren ob das delta schon heruntergeladen wurde und eintragen, dass der user es heruntergeladen hat.
    public List<Delta> getNewDelta(long projectID, String user) {
        List<Delta> d = deltaRepo.findByRequestedByAndProject("", projectID);
        return d;
    }


    public List<Delta> getOldDelta(long projectID, String user) {
        List<Delta> oldDelta = deltaRepo.findByRequestedByAndProject(user, projectID);
        deltaRepo.deleteAll(oldDelta);
        System.out.println(oldDelta);
        fetchRequestService.deleteFetchRequest(projectID, user);
        return oldDelta;
    }

    public boolean addOldDelta(long projectID, String initialAddedBy, String requestedBy, String command, LocalDateTime initialAddedToServer, boolean wasAdmin) {
        removeOutDatedDeltas();
        deltaRepo.save(new Delta(initialAddedToServer, initialAddedBy, command, projectID, wasAdmin, requestedBy));
        projectRepo.findById(projectID).get().setLastUpdated(LocalDateTime.now());
        return true;
    }

    public long getRemoveTimeInMinutes() {
        return (REMOVE_TIME_DAYS * 24 * 60);
    }

    private void removeOutDatedDeltas() {
        List<Delta> toRemove = deltaRepo.findByAddedToServerIsBeforeAndRequestedBy(LocalDateTime.now().minusDays(REMOVE_TIME_DAYS), "");
        deltaRepo.deleteAll(toRemove);
    }
}
