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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Controller for user and project management
 * <p>
 * All interactions with user management happens here. Provides functions to add and remove users and create new
 * projects.
 * A project gets deleted, if all users leave the project.
 */
@RestController
@RequestMapping("/OnlineDatabase")
public class ProjectParticipantsController {

    @Autowired
    private final ProjectParticipantService service;

    /**
     * The constructor of the ProjectParticipantsController.
     *
     * @param ppService the service, which contains the logic for the Controller.
     */
    public ProjectParticipantsController(ProjectParticipantService ppService) {
        this.service = ppService;
    }

    /**
     * Adds a user to a project, if it's possible. If a user tries to join a project, where they already participate,
     * they also get the initial for the project.
     *
     * @param user      the user who wants to join the project.
     * @param projectId the project, where the user wants to participate.
     * @return the initial for the project, if the user participates now in the project, if not, it returns an empty
     * String. ("").
     */
    @PostMapping("/addUser/{id}")
    public String addUser(@RequestAttribute String user, @PathVariable("id") long projectId) {
        return service.addUser(user, projectId);
    }

    /**
     * Removes a user from a project. A user can remove himself.
     * <p>
     * The method also checks if the user is an admin, if he wants to remove another person from the project.
     * If the user is the last user in the project, and they can remove themselves, the project gets deleted eminently.
     *
     * @param user         the user who wants to remove someone.
     * @param projectId    the project, where a user should be removed from.
     * @param userToRemove all information, which are necessary to remove a user from a project.
     * @return true, if the user is removed, false if it was not possible.
     */
    @DeleteMapping(value = "/removeUser/{id}")
    public boolean removeUser(@RequestAttribute String user, @PathVariable("id") long projectId,
                              @RequestParam String userToRemove) {
        if (user.equals(userToRemove)) {
            return service.leaveProject(user, projectId);
        }
        return service.removeOtherUser(user, projectId, userToRemove);
    }

    /**
     * Adds a new project. The user, who initializes the project is the admin.
     *
     * @param user           who wants to create a project.
     * @param projectDetails the initial for the project, which are needed for the client.
     * @return the projectId for the new project.
     */
    @PostMapping(value = "/newProject")
    public long addProject(@RequestAttribute String user, @RequestBody String projectDetails) {
        return service.addProject(user, projectDetails);
    }

    /**
     * Returns all user of the project, which currently participate.
     *
     * @param user      the user, who wants to know, who participates.
     * @param projectId the id, from which project the participants were recommended.
     * @return a list with users.
     */
    @GetMapping(value = "/{id}/participants")
    public List<String> getParticipants(@RequestAttribute String user, @PathVariable("id") long projectId) {
        return service.getParticipants(projectId);
    }

    /**
     * Provides the admin.
     *
     * @param user      the participant, who wants to know who the admin is.
     * @param projectId the id from which the admin is recommended.
     * @return the admin≤
     */
    @GetMapping(value = "/{id}/admin")
    public String getAdmin(@RequestAttribute String user, @PathVariable("id") long projectId) {
        return service.getAdmin(projectId);
    }
}