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
import com.pseandroid2.dailydataserver.onlineDatabase.requestParameters.deltaController.ProvideOldDataParameter;
import com.pseandroid2.dailydataserver.onlineDatabase.requestParameters.deltaController.SaveDeltaParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * #TODO Testen
 */

/**
 * Controller for interaction with the server for delta handling.
 * <p>
 * The client can provide new and old Delta and can receive Deltas. They also get the information, how long Deltas can be saved.
 * If a client wants Deltas they receive all Deltas, old ones, he requested, and new one, they don't already have.
 * There is no warranty, that all old Deltas, which a user had recommended are there. Maybe some of them get deleted, caused by place problems.
 */
@RestController
@RequestMapping("/OnlineDatabase/Delta")
public class DeltaController {

    @Autowired
    private final DeltaOrganisationService service;

    public DeltaController(DeltaOrganisationService service){
        this.service = service;
    }
    /**
     * creates a new delta for a project to save one command.
     *
     * @param projectId declares to which projects the command belongs (provided by client)
     * @param user      the user, who adds the Data (generated by the server)
     * @param params    the data which are recommended to save a delta. {@link SaveDeltaParameter} specifies all parameters. (provided by client)
     * @return true, if the delta could be saved, false if not
     */
    @PostMapping("/save/{id}")
    public boolean saveDelta(@PathVariable("id") long projectId, @RequestAttribute String user,
                             @RequestBody SaveDeltaParameter params) {
        return service.saveDelta(projectId,user, params.getCommand());
    }

    /**
     * Provides all new Deltas which belongs to a project, which the user don't have, and all old deltas which belongs to the user.
     *
     * @param projectId declares from which project the delta is recommended (provided in the URL by the client)
     * @param user      the user, who adds the Data (generated by the server)
     * @return a list of recommended Deltas
     */
    @GetMapping("/get/{id}")
    public List<Delta> getDelta(@PathVariable("id") long projectId, @RequestAttribute String user) {

        List<Delta> newDelta = service.getNewDelta(projectId,user);
        List<Delta> oldDelta = service.getOldDelta(projectId,user);
        newDelta.addAll(oldDelta);
        return newDelta;
    }

    /**
     * recreates an old Delta, for a defined person and project.
     *
     * @param projectId to which project the delta belong (provided in the URL by the client)
     * @param user      the user, who adds the Data (generated by the server)
     * @param params    the data which are recommended to save an old delta. {@link ProvideOldDataParameter} specifies all parameters. (provided by client)
     */
    @PostMapping("/provide/{id}")
    public boolean provideOldData(@PathVariable("id") long projectId, @RequestAttribute String user,
                                  @RequestBody ProvideOldDataParameter params) {
        return service.addOldDelta(projectId, params.getInitialAddedBy(), params.getForUser(), params.getCommand(), params.getInitialAdded(), params.isWasAdmin());
    }

    /**
     * Returns the period length, after which a new delta gets deleted.
     *
     * @return period length, after which a delta gets deleted.
     */
    @GetMapping("/time")
    public long getRemoveTime() {

        return service.getRemoveTimeInMinutes();
    }
}
