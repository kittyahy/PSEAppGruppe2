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
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.Project;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectParticipant;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectParticipantID;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectParticipantsRepository;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectRepository;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The service, which provides all logic for the Delta Controller.
 * <p>
 * All methods, which need a project and a user, which participates in the project, do not validate, if the user
 * participates in the project.
 * The only methods, which do not need this verification are getRemoveTimeInMinutes and getNewDelta.
 * <p>
 * The class returns, saves and deletes Deltas with different conditions. And can proivides the time, after which the
 * delta going to be removed.
 */
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


    /**
     * The constructor for DeltaOrganisationService.
     *
     * @param deltaRepo           the DeltaRepository to save, delete and get the Deltas.
     * @param fetchRequestService The FetchRequest Service, to delete FetchRequests.
     * @param ppRepo              the projectParticipantsRepository, to get more information to the users.
     * @param projectRepo         the projectRepo to get information to the project and update them.
     */
    public DeltaOrganisationService(DeltaRepository deltaRepo, FetchRequestService fetchRequestService,
                                    ProjectParticipantsRepository ppRepo, ProjectRepository projectRepo) {
        this.deltaRepo = deltaRepo;
        this.fetchRequestService = fetchRequestService;
        this.ppRepo = ppRepo;
        this.projectRepo = projectRepo;
    }

    /**
     * This method saves a new Delta. And update the project, because anybody changed something in the project.
     * But first, it removes all Deltas, which has to be removed (removeOutDatedDeltas).
     * <p>
     * It checks:
     * If the user has not posted to many Deltas in MAX_POSTS_PER_DAY
     * If the command itself is smaller than a MAX_COMMAND_SIZE
     *
     * @param projectID      the project, to which the Delta belongs.
     * @param user           the user, which provides the Delta
     * @param projectCommand the command, which the user changed.
     * @return true, if the delta could be saved, false if it was not possible.
     */
    public boolean saveDelta(long projectID, String user, String projectCommand) {
        removeOutDatedDeltas();
        long amountOfPosts = deltaRepo.countByUserAndAddedToServerIsAfter(user, LocalDateTime.now().minusDays(1));
        if (amountOfPosts >= MAX_POSTS_PER_DAY) {
            return false;
        }

        if (projectCommand.getBytes().length > MAX_COMMAND_SIZE) {
            return false;
        }

        ProjectParticipant participant = ppRepo.findById(new ProjectParticipantID(user, projectID)).get();
        Delta delta = new Delta(user, projectCommand, projectID, (participant.getRole() == Role.ADMIN));
        deltaRepo.save(delta);

        updateProject(projectID);

        return true;

    }


    /**
     * Returns all new Deltas. These are those Deltas, which are not recommended by any user.
     * <p>
     * Does not save the user which needs the Deltas.
     *
     * @param projectID the project, from which the deltas were recommended.
     * @param user      the user, who wants the Deltas.
     * @return a list of all recommeded Deltas.
     */
    public List<Delta> getNewDelta(long projectID, String user) {
        List<Delta> deltas = deltaRepo.findByRequestedByAndProject("", projectID);
        return deltas;
    }

    /**
     * Returns all recommended Deltas from a specified user and project.
     * If the list is not empty, all FetchRequests
     * for the user and project going to be deleted and the project gets updated, that there were changes.
     *
     * @param projectID the project, for Deltas could be there.
     * @param user      the user, who needs oldDeltas.
     * @return a list of recommended Deltas.
     */
    public List<Delta> getOldDelta(long projectID, String user) {
        List<Delta> oldDelta = deltaRepo.findByRequestedByAndProject(user, projectID);
        deltaRepo.deleteAll(oldDelta);

        if (!oldDelta.isEmpty()) {
            fetchRequestService.deleteFetchRequest(projectID, user);
            updateProject(projectID);
        }

        return oldDelta;
    }

    /**
     * Recreates a Delta with given parameters.
     * <p>
     * It updates the project, that something changed and remove outdated Deltas.
     *
     * @param projectID            the project, to which the Delta belongs.
     * @param initialAddedBy       The user, which first published the change and created the original Delta.
     * @param requestedBy          the user who wants the old Deltas
     * @param command              the command, what was changed.
     * @param initialAddedToServer the point of time the Delta, when it was first published.
     * @param wasAdmin             if the user, who first published the Delta, was admin to the point of time, the
     *                             Delta initially was created.
     * @return if the old Delta could be saved.
     */
    public boolean addOldDelta(long projectID, String initialAddedBy, String requestedBy, String command,
                               LocalDateTime initialAddedToServer, boolean wasAdmin) {
        removeOutDatedDeltas();
        deltaRepo.save(new Delta(initialAddedToServer, initialAddedBy, command, projectID, wasAdmin, requestedBy));
        updateProject(projectID);
        return true;
    }

    /***
     * Provides the time, after which new Deltas going to be deleted.
     * @return the recommended period in minutes.
     */
    public long getRemoveTimeInMinutes() {
        return (REMOVE_TIME_DAYS * 24 * 60);
    }


    private void removeOutDatedDeltas() {
        List<Delta> toRemove =
                deltaRepo.findByAddedToServerIsBeforeAndRequestedBy(LocalDateTime.now().minusDays(REMOVE_TIME_DAYS),
                        "");
        deltaRepo.deleteAll(toRemove);
    }

    private void updateProject(long projectID) {
        Project project = projectRepo.findById(projectID).get();
        project.setLastUpdated(LocalDateTime.now());
        projectRepo.save(project);
    }
}
