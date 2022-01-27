package com.pseandroid2.dailydataserver.onlineDatabase;


import com.pseandroid2.dailydataserver.onlineDatabase.DeltaDB.Delta;
import com.pseandroid2.dailydataserver.onlineDatabase.DeltaDB.DeltaID;
import com.pseandroid2.dailydataserver.onlineDatabase.DeltaDB.DeltaRepository;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectParticipants;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectParticipantsID;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.ProjectParticipantsRepository;
import com.pseandroid2.dailydataserver.onlineDatabase.userAndProjectManagementDB.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class DeltaOrganisationService {

    private static final long MAX_POSTS_PER_DAY = 128;
    private static final int MAX_COMMANDSIZE = 1000;
    @Autowired
    private final DeltaRepository deltaRepo;

    @Autowired
    private final ProjectParticipantsRepository ppRepo;

    public DeltaOrganisationService(DeltaRepository deltaRepo,ProjectParticipantsRepository ppRepo){
        this.deltaRepo = deltaRepo;
        this.ppRepo = ppRepo;
    }

    public boolean saveDelta(long projectID,String user,String projectCommand){

        long amountOfPosts = deltaRepo.countByUserAndAddedToServerIsAfter(user,LocalDateTime.now().minusDays(1));

        if(amountOfPosts >= MAX_POSTS_PER_DAY){ //Amount of Post per Day and per User
            return false;
        }
       if(projectCommand.getBytes().length > MAX_COMMANDSIZE){ //size of the command
           return false;
       };
        ProjectParticipants participant = ppRepo.findById(new ProjectParticipantsID(user,projectID)).get();
        deltaRepo.save(new Delta(user,projectCommand,projectID,(participant.getRole()== Role.ADMIN)));
        return true;

    }


    public List<Delta> getDelta(long projectID,String user){
        List<Delta> newDelta = deltaRepo.findByProjectAndDownloadedBy_UserIsNotAndRequestedByEquals(projectID,user,"");
        //new delta
        for (Delta delta : newDelta){
            Delta d = deltaRepo.getById(new DeltaID(delta.getAddedToServer(),user));
            d.addDownloadedBy(ppRepo.findById(new ProjectParticipantsID(user,projectID)).get());
            //delete the delta, if everyone has it.
           if(d.getDownloadedBy().size() == ppRepo.findByProject(projectID).size()) {
               deltaRepo.delete(d);
           }
            delta.setDownloadedBy(new HashSet<>());
        }

        //old delta
        List<Delta> oldDelta = deltaRepo.findByRequestedByAndProject(user,projectID);
        deltaRepo.deleteAll(oldDelta);
        List<Delta> allDelta = new ArrayList<>();


        allDelta.addAll(oldDelta);
        allDelta.addAll(newDelta);
        return allDelta;

    }
}
