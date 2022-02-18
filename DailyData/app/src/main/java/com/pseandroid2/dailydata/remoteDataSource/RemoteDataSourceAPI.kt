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

package com.pseandroid2.dailydata.remoteDataSource

import android.graphics.Bitmap
import com.pseandroid2.dailydata.model.users.SimpleUser
import com.pseandroid2.dailydata.model.users.User
import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueueObserver
import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandInfo
import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueueObserver
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerNotReachableException
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.forRepoReturns.PostPreviewWithPicture
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.forRepoReturns.TemplateDetailWithPicture
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.FetchRequest
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import com.pseandroid2.dailydata.remoteDataSource.userManager.SignInTypes
import com.pseandroid2.dailydata.remoteDataSource.userManager.UserAccount
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * This class allows the repository to use all the required server (and firebase) sided features
 */
class RemoteDataSourceAPI @Inject constructor(uAccount: UserAccount?, sManager: ServerManager?) {
    private val userAccount: UserAccount = uAccount ?: UserAccount(FirebaseManager(null))
    private val serverManager: ServerManager = sManager ?: ServerManager(RESTAPI())

// -----------------------------FireBase-------------------------------
    // -----------------------------UserAccount-------------------------------
    /**
     * Registers a new user with the requested sign in type.
     * Note: The registration will also fail, if there already exists an account with the registration parameters
     *
     * @param eMail:    The email of the user that should be registered
     * @param password: The password of the user that should be registered
     * @param type:     Through which method should the user be register (eg email)
     * @return FirebaseReturnOptions: If the registration succeeded or failed
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun registerUser(
        eMail: String,
        password: String,
        type: SignInTypes
    ): FirebaseReturnOptions {
        return userAccount.registerUser(eMail, password, type)
    }

    /**
     * Signs in an already existing user with the passed sign in parameters
     *
     * @param eMail:    The email of the user that should be signed in
     * @param password: The password of the user that should be signed in
     * @param type:     Through which method should the user be signed in (eg email)
     * @return FirebaseReturnOptions: If the sign in succeeded or failed
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun signInUser(
        eMail: String,
        password: String,
        type: SignInTypes
    ): FirebaseReturnOptions {
        return userAccount.signInUser(eMail, password, type)
    }

    /**
     * Signs out the currently logged in user
     *
     * @return FirebaseReturnOptions: The success status of the request
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun signOut(): FirebaseReturnOptions {
        return userAccount.signOut()
    }

    // -----------------------------UserDetails-------------------------------
    /**
     * Get the currently signed in user.
     */
    fun getUser(): User {
        @Suppress("Deprecation")
        return SimpleUser(getUserID(), getUserName())
    }

    /**
     * Get the id of the currently signed in user
     *
     * @return String: The firebase ID of the signed in user. If no user is signed in return ""
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    @Deprecated("getting userids directly is deprecated", ReplaceWith("getUser()"))
    fun getUserID(): String {
        return userAccount.getUserID()
    }

    /**
     * Get the username of the currently signed in user
     *
     * @return String: The username of the signed in user (if existing). If no user is signed in return ""
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    @Deprecated("getting userids directly is deprecated", ReplaceWith("getUser()"))
    fun getUserName(): String {
        return userAccount.getUserName()
    }

    /**
     * Get the email of the currently signed in user
     *
     * @return String: The email of the signed in user (if existing). If no user is signed in return ""
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    @Deprecated("getting userids directly is deprecated")
    fun getUserEMail(): String {
        return userAccount.getUserEMail()
    }

    /**
     * Get the user photo url of the currently signed in user
     *
     * @return String: The photoURL of the signed in user (if existing). If no user is signed in return ""
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    @Deprecated("getting userids directly is deprecated")
    fun getUserPhotoUrl(): String {
        return userAccount.getUserPhotoUrl()
    }

// -----------------------------ServerAccess-------------------------------
    // -----------------------------GreetingController-------------------------------
    /**
     * Checks if it possible to connect to our server
     *
     * @return Boolean: If a server connection possible return true, else return false
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun connectionToServerPossible(): Boolean {
        return serverManager.connectionToServerPossible()
    }

    // -----------------------------PostsController-------------------------------
    /**
     * Gets post previews from the server
     *
     * @return Collection<PostPreviewWithPicture>: The previews of the posts
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getPostPreviews(): Collection<PostPreviewWithPicture> {
        val authToken: String = userAccount.getToken()
        return serverManager.getAllPostPreview(authToken)
    }

    /**
     * Gets the details of a post
     *
     * @param fromPost: The id from the searched post
     * @return Collection<TemplateDetailWithPicture>: Returns the detailed post belonging to the post id
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getPostDetail(fromPost: Int): Collection<TemplateDetailWithPicture> {
        val authToken: String = userAccount.getToken()
        return serverManager.getPostDetail(fromPost, authToken)
    }

    /**
     * Gets the project template of a post
     *
     * @param fromPost: The post from which the project template should be downloaded
     * @return String - The requested project template as JSON
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getProjectTemplate(fromPost: Int): String {
        val authToken: String = userAccount.getToken()
        return serverManager.getProjectTemplate(fromPost, authToken)
    }

    /**
     * Gets one graph template of a post
     *
     * @param fromPost:         The post from which the graph templates should be downloaded
     * @param templateNumber:   Which graph template should be downloaded from the post
     * @return String - The requested graph template as JSON
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getGraphTemplate(fromPost: Int, templateNumber: Int): String {
        val authToken: String = userAccount.getToken()
        return serverManager.getGraphTemplate(fromPost, templateNumber, authToken)
    }

    // Wish-criteria
    /**
     * Uploads a post to the server
     *
     * @param postPreview:      The preview of the post that should be added. The first element of the Pair has the preview picture and the second element has the title
     * @param projectTemplate:  The project template. The first element of the outer Pair is the template as a JSON and the second element is the inner Pair.
     *                              The inner Pair has as it first element the template detail image and as its second element the title of the template
     * @param graphTemplates:   The graph templates. Every list element is a graph template.
     *                              The first element of the outer Pair is the template as a JSON and the second element is the inner Pair.
     *                              The inner Pair has as it first element the template detail image and as its second element the title of the template
     * @return Int: The PostID of the new post. -1 if the call didn't succeed, 0 if the user reached his limit of uploaded posts
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun uploadPost(
        postPreview: Pair<Bitmap, String>,
        projectTemplate: Pair<String, Pair<Bitmap, String>>,
        graphTemplates: List<Pair<String, Pair<Bitmap, String>>>
    ): Int {
        val authToken: String = userAccount.getToken()
        return serverManager.addPost(postPreview, projectTemplate, graphTemplates, authToken)
    }

    // Wish-criteria
    /**
     * Deletes a post from the server
     *
     * @param postID: The id of the post that should be removed from the server
     * @return Boolean: Did the operation succeed
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun removePost(postID: Int): Boolean {
        val authToken: String = userAccount.getToken()
        return serverManager.removePost(postID, authToken)
    }

    // -----------------------------ProjectParticipantsController-------------------------------
    /**
     * Lets the currently signed in user join the project and returns the project information
     *
     * @param projectID: The id of the project to which the user is to be added
     * @return String: Returns the project information as a JSON. (Returns "" on error)
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun joinProject(projectID: Long): String {
        val authToken: String = userAccount.getToken()
        return serverManager.addUser(projectID, authToken)
    }

    /**
     * Removes a user from a project
     *
     * @param userToRemove: The id of the user that should be removed from the project
     * @param projectID:    The id of the project from which the user should be removed
     * @return Boolean: Did the operation succeed
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun removeUser(userToRemove: String, projectID: Long): Boolean {
        val authToken: String = userAccount.getToken()
        return serverManager.removeUser(userToRemove, projectID, authToken)
    }

    /**
     * Creates a new online project on the server and returns the id
     *
     * @param projectDetails: The details of a project (project name, project description, table format, ...) as JSON
     * @return LONG: Returns the id of the created project. Returns -1 if an error occurred
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun createNewOnlineProject(projectDetails: String): Long {
        val authToken: String = userAccount.getToken()
        return serverManager.addProject(authToken, projectDetails)
    }

    /**
     * Gets all the project members
     *
     * @param projectID: The id of the project whose user should be returned
     * @return Collection<String>: The participants of the project. Returns empty list on error
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getProjectParticipants(projectID: Long): Collection<String> {
        val authToken: String = userAccount.getToken()
        return serverManager.getProjectParticipants(authToken, projectID)
    }

    /**
     * Is the user a project participant from the project.
     * (The user who send the authToken has to be a member of the project)
     *
     * @param authToken:    The token from the currently logged in user
     * @param userID:       The userID of the user, that should be checked, if it is part of the project
     * @return Boolean: True if the server call succeed and the user is part of the project. Otherwise false
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun isProjectParticipant(authToken: String, projectID: Long, userID: String): Boolean {
        return serverManager.isProjectParticipant(authToken, projectID, userID)
    }

    /**
     * Gets the admin of the project
     *
     * @param projectID: The id of the project whose admin should be returned
     * @return String: The UserID of the admin. Returns "" on error
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getProjectAdmin(projectID: Long): String {
        val authToken: String = userAccount.getToken()
        return serverManager.getProjectAdmin(authToken, projectID)
    }

    // -----------------------------DeltaController-------------------------------
    /**
     * Sends newly added project commands to the server
     *
     * @param projectID:        The id of the project to which the project command should be added
     * @param projectCommands:  The project commands that should be send to the server (as JSON)
     * @return Collection<String>: The successfully uploaded project commands (as JSONs)
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun sendCommandsToServer(
        projectID: Long,
        projectCommands: Collection<String>
    ): Collection<String> {
        val authToken: String = userAccount.getToken()
        return serverManager.sendCommandsToServer(projectID, projectCommands, authToken)
    }

    /**
     * Gets the project commands of a project
     *
     * @param projectID: The id of the project whose deltas (projectCommands) you want to load into the ProjectCommandQueue
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getProjectCommandsFromServer(projectID: Long) {
        val authToken: String = userAccount.getToken()
        return serverManager.getProjectCommandsFromServer(projectID, authToken)
    }

    /**
     * Answers a fetch request
     *
     * @param projectCommand:   The projectCommand that should be uploaded to the server (as JSON)
     * @param forUser:          The id of the user whose fetch request is answered
     * @param initialAddedDate: When the project command was send to the server
     * @param projectID:        The id of the project belonging to the project command
     * @param wasAdmin:         Was the user a project administrator when the command was created
     * @return Boolean: Did the server call succeed
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun provideOldData(
        projectCommand: String,
        forUser: String,
        initialAddedDate: LocalDateTime,
        initialAddedBy: String,
        projectID: Long,
        wasAdmin: Boolean
    ): Boolean {
        val authToken: String = userAccount.getToken()
        return serverManager.provideOldData(
            projectCommand,
            forUser,
            initialAddedDate,
            initialAddedBy,
            projectID,
            wasAdmin,
            authToken
        )
    }

    /**
     * Gets the remove time from the server (in Minutes)
     *
     * @return Long: The time how long an project command can remain on the server until it gets deleted by the server. On error returns -1
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getRemoveTime(): Long {
        val authToken: String = userAccount.getToken()
        return serverManager.getRemoveTime(authToken)
    }

    // -----------------------------FetchRequestController-------------------------------
    /**
     * Sends a fetch request to the server
     *
     * @param projectID:    The id of the project to which the fetch request should be uploaded
     * @param requestInfo:  The fetch request as JSON
     * @return Boolean: Did the server call succeed
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun demandOldData(projectID: Long, requestInfo: String): Boolean {
        val authToken: String = userAccount.getToken()
        return serverManager.demandOldData(projectID, requestInfo, authToken)
    }

    /**
     * Fills the FetchRequestQueue with fetch requests from a project
     *
     * @param projectID: The id of the project from which the fetch requests should be downloaded
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getFetchRequests(projectID: Long) {
        val authToken: String = userAccount.getToken()
        return serverManager.getFetchRequests(projectID, authToken)
    }

    // -----------------------------ObserverLogic-------------------------------
    /**
     * Adds an observer to the fetch request queue
     *
     * @param observer: The observer that should be added to the FetchRequestQueue
     */
    suspend fun addObserverToFetchRequestQueue(observer: FetchRequestQueueObserver) {
        serverManager.addObserverToFetchRequestQueue(observer)
    }

    /**
     * Removes an observer from the fetch request queue
     *
     * @param observer: The observer that should be removed from the FetchRequestQueue
     */
    suspend fun unregisterObserverFromFetchRequestQueue(observer: FetchRequestQueueObserver) {
        serverManager.unregisterObserverFromFetchRequestQueue(observer)
    }

    /**
     * Adds an observer to the project command queue
     *
     * @param observer: The observer that should be added to the ProjectCommandQueue
     */
    suspend fun addObserverToProjectCommandQueue(observer: ProjectCommandQueueObserver) {
        serverManager.addObserverToProjectCommandQueue(observer)
    }

    /**
     * Removes an observer from the project command queue
     *
     * @param observer: The observer that should be removed from the ProjectCommandQueue
     */
    suspend fun unregisterObserverFromProjectCommandQueue(observer: ProjectCommandQueueObserver) {
        serverManager.unregisterObserverFromProjectCommandQueue(observer)
    }

    // QueueLogic
    /**
     * @return INT: The length of the FetchRequestQueue
     */
    fun getFetchRequestQueueLength(): Int {
        return serverManager.getFetchRequestQueueLength()
    }

    /**
     * Returns a fetchRequest from the queue and removes it from the queue
     *
     * @return String: The returned fetchRequest as JSON if there is one in the queue. (Returns null if the queue is empty)
     */
    fun getFetchRequestFromQueue(): FetchRequest? {
        return serverManager.getFetchRequestFromQueue()
    }

    /**
     * @return INT: The length of the ProjectCommandQueue
     */
    fun getProjectCommandQueueLength(): Int {
        return serverManager.getProjectCommandQueueLength()
    }


    /**
     * Returns a project command from the queue and removes it from the queue
     *
     * @return String: The returned project command as JSON if there is one in the queue. (Returns null if the queue is empty)
     */
    fun getProjectCommandFromQueue(): ProjectCommandInfo? {
        return serverManager.getProjectCommandFromQueue()
    }
}