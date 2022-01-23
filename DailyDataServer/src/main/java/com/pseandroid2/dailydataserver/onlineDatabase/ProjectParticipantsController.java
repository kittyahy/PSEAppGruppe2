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

import com.pseandroid2.dailydataserver.Communication.RequestParameter;
import com.pseandroid2.dailydataserver.onlineDatabase.requestParameters.projectParticipantsController.RemoveUserParameter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for user and project management
 * <p>
 * All interactions with user management happens here. Provides functions to add and remove users and create new projects.
 * A project gets deleted, if all users leave the project.
 * #TODO Testen
 */
@RestController
@RequestMapping("/OnlineDatabase")
public class ProjectParticipantsController {

    private final ProjectParticipantService service;

    public ProjectParticipantsController(ProjectParticipantService ppService) {
        this.service = ppService;
    }

    /**
     * Adds a user to a project.
     *
     * @param user      the user who wants to join the project (generated by the server)
     * @param projectId the project, where the user wants to participate (provided by the client)
     * @param param     the data, for authenticate the user. {@link RequestParameter} specifies this parameter.
     * @return false, if the project is full or the user can not participate.
     */
    @PostMapping("/addUser/{id}")
    public boolean addUser(@RequestAttribute String user, @PathVariable("id") long projectId, @RequestBody RequestParameter param) {
        return service.addUser(user, projectId);
    }

    /**
     * Removes a user from a project. A user can remove himself.
     * <p>
     * The method also checks if the user is an admin, if he wants to remove another person from the project.
     *
     * @param user      the user who wants to remove someone (generated by the server)
     * @param projectId the project, where a user should be removed from.
     * @param params    all information, which are necessary to remove a user from a project. {@link RemoveUserParameter} specifies all parameters.
     * @return true, if the user is removed, false if it was not possible.
     */
    @DeleteMapping("/removeUser/{id}")
    public boolean removeUser(@RequestAttribute String user, @PathVariable("id") long projectId, @RequestBody RemoveUserParameter params) {
        if (user.equals(params.getUserToRemove())) {
            return service.leaveProject(user, projectId);
        }
        return service.removeOtherUser(user, projectId, params.getUserToRemove());
    }

    /**
     * Adds a new project. The user, who initializes the project is the admin.
     *
     * @param user  who wants to create a project. (generated by the server)
     * @param param the data, for authenticate the user. {@link RequestParameter} specifies this parameter.
     * @return the projectId for the new project.
     */
    @PostMapping("/newProject")
    public long addProject(@RequestAttribute String user, @RequestBody RequestParameter param) {
        return service.addProject(user);
    }

    /**
     * Returns all names of the current participants (inclusive the admin).
     *
     * @param user      the user, who wants to know, who participates (generated by the server)
     * @param projectId the id, from which project the participants were recommended (provided by the client)
     * @param param     the data, for authenticate the user. {@link RequestParameter} specifies this parameter.
     * @return a list with all names of the current participants
     */
    @GetMapping("/{id}/participants")
    public List<String> getParticipants(@RequestAttribute String user, @PathVariable("id") long projectId, @RequestBody RequestParameter param) {
        return service.getParticipants(projectId);
    }

    /**
     * Provides the username of the admin.
     *
     * @param user      name of the participant, who wants to know who the admin is. (generated by the server)
     * @param projectId the id from which the admin is recommended. (provided by the client)
     * @param param     the data, for authenticate the user. {@link RequestParameter} specifies this parameter.
     * @return
     */
    @GetMapping("/{id}/admin")
    public String getAdmin(@RequestAttribute String user, @PathVariable("id") long projectId, @RequestBody RequestParameter param) {
        return service.getAdmin(projectId);
    }
}


