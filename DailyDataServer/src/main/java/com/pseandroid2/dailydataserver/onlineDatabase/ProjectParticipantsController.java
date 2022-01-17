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

import com.pseandroid2.dailydataserver.RequestParameter;
import com.pseandroid2.dailydataserver.onlineDatabase.requestParameters.projectParticipantsController.RemoveUserParameter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * All interactions with usermanagement happens here. Provides functions to add and remove users and create new projects.
 * #TODO Testen, JavaDoc
 */
@RestController
@RequestMapping("/OnlineDatabase")
public class ProjectParticipantsController {

    /**
     * Adds a user to a project.
     *
     * @param user      the user who wants to join the project. generated by the server.
     * @param projectId the project, where the user wants to participate.  provided by the client.
     * @return false, if the project is full or the user can not participate.
     */
    @PostMapping("/addUser/{id}")
    public boolean addUser(@RequestAttribute String user, @PathVariable("id") long projectId, @RequestBody RequestParameter param) {
        return true;
    }

    /**
     * Removes a user from a project. A user can remove himself.
     *
     * @param user         the user who wants to remove someone, generated by the server.
     * @param projectId    the project, where the userToRemove should be removed from.
     * @return true, if the user is removed, false if it was not possible.
     */
    @DeleteMapping("/removeUser/{id}")
    public boolean removeUser(@RequestAttribute String user, @PathVariable("id") long projectId, @RequestBody RemoveUserParameter param) {
        return true;
    }

    /**
     * Adds a new project. The user, who initializes the project is the admin.
     *
     * @param user  who wants to create a project.
     * @return the projectId for the new project.
     */
    @PostMapping("/newProject")
    public long addProject( @RequestAttribute String user, @RequestBody RequestParameter param) {
        return 0;
    }
}
