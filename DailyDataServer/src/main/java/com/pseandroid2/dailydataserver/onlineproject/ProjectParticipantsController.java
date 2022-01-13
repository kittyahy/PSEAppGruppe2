package com.pseandroid2.dailydataserver.onlineproject;

import org.springframework.web.bind.annotation.*;

/**
 * All interactions with usermanagement happens here. Provides functions to add and remove users and create new projects.
 */

@RestController
@RequestMapping("/OnlineDatabase")
public class ProjectParticipantsController {

    /**
     * Adds a user to a project.
     *
     * @param token     to verify the user, provided by the client.
     * @param user      the user who wants to join the project. generated by the server.
     * @param projectId the project, where the user wants to participate.  provided by the client.
     * @return false, if the project is full or the user can not participate.
     */
    @PostMapping("/addUser/{id}")
    public boolean addUser(@RequestHeader String token, @RequestAttribute String user, @PathVariable("id") long projectId) {
        return true;
    }

    /**
     * Removes a user from a project. A user can remove himself.
     *
     * @param token        to verify the user, provided by the client.
     * @param user         the user who wants to remove someone, generated by the server.
     * @param projectId    the project, where the userToRemove should be removed from.
     * @param userToRemove the user, which should be removed.
     * @return true, if the user is removed, false if it was not possible.
     */
    @DeleteMapping("/removeUser/{id}")
    public boolean removeUser(@RequestHeader String token, @RequestAttribute String user, @PathVariable("id") long projectId, @RequestParam String userToRemove) {
        return true;
    }

    /**
     * Adds a new project. The user, who initializes the project is the admin.
     *
     * @param token to verify the user, provided by the client.
     * @param user  who wants to create a project.
     * @return the projectId for the new project.
     */
    @PostMapping("/newProject")
    public long addProject(@RequestHeader String token, @RequestAttribute String user) {
        return 0;
    }
}
