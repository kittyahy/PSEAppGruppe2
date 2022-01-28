package com.pseandroid2.dailydataserver.onlineDatabase;

import com.pseandroid2.dailydataserver.onlineDatabase.FetchRequestDB.FetchRequest;
import com.pseandroid2.dailydataserver.onlineDatabase.FetchRequestDB.FetchRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FetchRequestService {
    @Autowired
    private FetchRequestRepository repo;

    public FetchRequestService(FetchRequestRepository repo){
        this.repo = repo;
    }

    public void createFetchRequest(long projectID, String user, String requestInfo){
        repo.save(new FetchRequest(user,projectID,requestInfo));

    }

    public List<FetchRequest> getFetchRequests(long projectID){
        return repo.findByProject(projectID);
    }

    public void deleteFetchRequest(long projectID, String user){
        List<FetchRequest> toRemove = repo.findByUserAndProject(user,projectID);

        repo.deleteAll(toRemove);
    }

}
