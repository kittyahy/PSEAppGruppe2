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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * interface for the organisation of old Deltas.
 * <p>
 * the user can post request for old Data und can get request for a project to help other participants.
 */
@RestController
@RequestMapping(value = "/OnlineDatabase/request")
public class FetchRequestController {

    @Autowired
    private final FetchRequestService service;

    /**
     * The constructor to create a fetchRequestController.
     *
     * @param service the service, which contains the logic for the controller.
     */
    public FetchRequestController(FetchRequestService service) {
        this.service = service;
    }

    /**
     * Saves a request for old Data by the client.
     * Another participant of the same project can fetch such requests.
     *
     * @param user        the client, who needs old Data.
     * @param projectID   the project to which the request belongs.
     * @param requestInfo all information, which are necessary to save a fetchRequest, and another participant needs
     *                    to provide the right Deltas.
     * @return if the a new fetch requests was created
     */
    @PostMapping(value = "/need/{id}")
    public boolean demandOldData(@RequestAttribute String user, @PathVariable(value = "id") long projectID,
                                 @RequestBody String requestInfo) {
        return service.createFetchRequest(projectID, user, requestInfo);
    }

    /**
     * Provides all {@link FetchRequest}s, which belongs to the project.
     *
     * @param projectId the project, to which the requests belong.
     * @return a list of {@link FetchRequest}.
     */

    @GetMapping(value = "/provide/{id}")
    public List<FetchRequest> getFetchRequests(@PathVariable("id") long projectId) {

        return service.getFetchRequests(projectId);
    }

}
