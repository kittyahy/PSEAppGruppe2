package com.pseandroid2.dailydataserver.onlineDatabase;


import com.pseandroid2.dailydataserver.onlineDatabase.DeltaDB.Delta;
import com.pseandroid2.dailydataserver.onlineDatabase.DeltaDB.DeltaRepository;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectParticipants;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectParticipantsID;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectParticipantsRepository;
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
    private final ProjectParticipantsRepository ppRepo;

    public DeltaOrganisationService(DeltaRepository deltaRepo, ProjectParticipantsRepository ppRepo) {
        this.deltaRepo = deltaRepo;
        this.ppRepo = ppRepo;
    }

    //#TODO Kann nicht: downloadedBy
    public boolean saveDelta(long projectID, String user, String projectCommand) {
        removeOutDatedDeltas();
        long amountOfPosts = deltaRepo.countByUserAndAddedToServerIsAfter(user, LocalDateTime.now().minusDays(1));
        if (amountOfPosts >= MAX_POSTS_PER_DAY) { //Amount of Post per Day and per User
            return false;
        }

        //size of the command
        if (projectCommand.getBytes().length > MAX_COMMAND_SIZE) {
            return false;
        }
        ;
        ProjectParticipants participant = ppRepo.findById(new ProjectParticipantsID(user, projectID)).get();
        Delta delta = new Delta(user, projectCommand, projectID, (participant.getRole() == Role.ADMIN));
        deltaRepo.save(delta);
        return true;

    }

    // #TODO Kann nicht: Kontrollieren ob das delta schon heruntergeladen wurde und eintragen, dass der user es heruntergeladen hat.
    public List<Delta> getNewDelta(long projectID, String user) {
        List<Delta> d = deltaRepo.findByRequestedByAndProject("", projectID);
        System.out.println(d);
        return d;
    }


    public List<Delta> getOldDelta(long projectID, String user) {
        List<Delta> oldDelta = deltaRepo.findByRequestedByAndProject(user, projectID);
        deltaRepo.deleteAll(oldDelta);

        return oldDelta;
    }

    //#TODO fetch requests müssen noch gelöscht werden
    public boolean addOldDelta(long projectID, String initialAddedBy, String requestedBy, String command, LocalDateTime initialAddedToServer, boolean wasAdmin) {
        removeOutDatedDeltas();
        deltaRepo.save(new Delta(initialAddedToServer, initialAddedBy, command, projectID, wasAdmin, requestedBy));
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
