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

/**
 * Provides the logic for the FetchRequestController
 * <p>
 * This class saves and provides FetchRequests.
 */
@Service
public class FetchRequestService {
    @Autowired
    private final FetchRequestRepository repo;

    /**
     * The Constructor of the FetchRequest Service.
     *
     * @param repo the Repository, which provides functions to save and get Fetch Requests.
     */
    public FetchRequestService(FetchRequestRepository repo) {
        this.repo = repo;
    }

    /**
     * Creates a new FetchRequest.
     * <p>
     * A user declares which old Deltas they need.
     *
     * @param projectID   the id from the project, for which the FetchRequest is.
     * @param user        the user, who wants  Old Delta.
     * @param requestInfo the information, which Deltas are needed.
     * @return if a new FetchRequest was created.
     */
    public boolean createFetchRequest(long projectID, String user, String requestInfo) {
        repo.save(new FetchRequest(user, projectID, requestInfo));
        return true;
    }

    /**
     * Provides all FetchRequests, which belongs to a project.
     * <p>
     * It also returns the FetchRequests, which the user created themselves, if there are any.
     *
     * @param projectID the project, from which the FetchRequest are recommended.
     * @return all recommended FetchRequests.
     */
    public List<FetchRequest> getFetchRequests(long projectID) {
        return repo.findByProject(projectID);
    }

    /**
     * Deletes all FetchRequests, which were created by a specified user in a specified project.
     *
     * @param projectID the project, from which the Fetch Requests were recommended.
     * @param user      the user, which provided the FetchRequest.
     */
    public void deleteFetchRequest(long projectID, String user) {
        List<FetchRequest> toRemove = repo.findByUserAndProject(user, projectID);

        repo.deleteAll(toRemove);
    }

}
