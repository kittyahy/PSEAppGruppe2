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

import com.pseandroid2.dailydataserver.onlineDatabase.FetchRequestDB.FetchRequest;
import com.pseandroid2.dailydataserver.onlineDatabase.FetchRequestDB.FetchRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FetchRequestService {
    @Autowired
    private final FetchRequestRepository repo;

    public FetchRequestService(FetchRequestRepository repo) {
        this.repo = repo;
    }

    public void createFetchRequest(long projectID, String user, String requestInfo) {
        repo.save(new FetchRequest(user, projectID, requestInfo));

    }

    public List<FetchRequest> getFetchRequests(long projectID) {
        return repo.findByProject(projectID);
    }

    public void deleteFetchRequest(long projectID, String user) {
        List<FetchRequest> toRemove = repo.findByUserAndProject(user, projectID);

        repo.deleteAll(toRemove);
    }

}
