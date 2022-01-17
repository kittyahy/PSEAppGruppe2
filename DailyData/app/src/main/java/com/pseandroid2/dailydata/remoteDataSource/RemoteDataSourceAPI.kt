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

import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueueObserver
import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueueObserver
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import com.pseandroid2.dailydata.remoteDataSource.userManager.SignInTypes
import com.pseandroid2.dailydata.remoteDataSource.userManager.UserAccount
import java.time.LocalDateTime

class RemoteDataSourceAPI {
    private val userAccount: UserAccount
    private val serverManager: ServerManager

    init {
        userAccount = UserAccount()
        serverManager = ServerManager()
    }

// -----------------------------FireBase-------------------------------
    // -----------------------------UserAccount-------------------------------
    /**
     * @param eMail: The email of the user that should be registered
     * @param password: The password of the user that should be registered
     * @param type: Through which method should the user be register (eg email)
     */
    fun registerUser(eMail: String, password: String, type: SignInTypes) : FirebaseReturnOptions {
        return userAccount.registerUser(eMail, password, type)
    }

    /**
     * @param eMail: The email of the user that should be signed in
     * @param password: The password of the user that should be signed in
     * @param type: Through which method should the user be signed in (eg email)
     */
    fun signInUser(eMail: String, password: String, type: SignInTypes) : FirebaseReturnOptions {
        return userAccount.signInUser(eMail, password, type)
    }

    /**
     * @return FirebaseReturnOptions: The success status of the request
     */
    fun signOut() : FirebaseReturnOptions {
        return userAccount.signOut()
    }

    // -----------------------------UserDetails-------------------------------
    /**
     * @return String: The firebase ID of the signed in user. If no user is signed in return ""
     */
    fun getUserID(): String {
        return userAccount.getUserID()
    }

    /**
     * @return String: The username of the signed in user. If no user is signed in return ""
     */
    fun getUserName(): String {
        return userAccount.getUserName()
    }

    /**
     * @return String: The email of the signed in user (if existing). If no user is signed in return ""
     */
    fun getUserEMail(): String {
        return userAccount.getUserEMail()
    }

    /**
     * @return String: The photoURL of the signed in user (if existing). If no user is signed in return ""
     */
    fun getUserPhotoUrl(): String {
        return userAccount.getUserPhotoUrl()
    }


    // -----------------------------Authentification-------------------------------
    /** // TODO: vllt das hier verbergen und Token nicht an Repository weitergeben
     * @return String: The token of the signed in user. If no user is signed in return ""
     */
    fun getToken(): String {
        return userAccount.getToken()
    }

// -----------------------------ServerAccess-------------------------------
    // -----------------------------GreetingController-------------------------------
    /**
     * @return Boolean: If a server connection possible return true, else return false
     */
    fun connectionToServerPossible(): Boolean {
        return serverManager.greet()

        return false;
    }

    // -----------------------------PostsController-------------------------------
    /**
     * @return Collection<String>: The previews of the posts (as JSONs)
     */
    fun getAllPostPreview(): Collection<String> {
        // TODO: Implement Method

        return mutableListOf("");
    }

    /**
     * @param fromPost: The id from the searched post
     * @return Collection<String>: Returns the detailed post belonging to the post id
     */
    fun getPostDetail(fromPost: Int): Collection<String> {
        // TODO: Implement Method

        return mutableListOf("");
    }

    /**
     * @param fromPost: The post from which the project template should be downloaded
     * @return String - The requested project template as JSON
     */
    fun getProjectTemplate(fromPost: Int): String { // TODO: Es existiert kein JSON Rückgabewert, lass dir was neues einfallen
        // TODO: Implement Method

        return "";
    }

    /** Downloads one graph template that is contained by a post
     *
     * @param fromPost: The post from which the graph templates should be downloaded
     * @param templateNumber: Which graph template should be downloaded from the post
     * @return String - The requested graph template as JSON
     */
    fun getGraphTemplate(fromPost: Int, templateNumber: Int): String {
        // TODO: Implement Method

        return "";
    }

    // Wish-Kriterium
    /**
     * @param postPreview: The preview of the post that should be added
     * @param projectTemplate: The project template that belongs to the post as JSON
     * @param Collection<String>: The graph templates that belong to the post as JSON
     */
    fun addPost(postPreview: String, projectTemplate: String, graphTemplate: Collection<String>) {
        // TODO: Implement Method

        return;
    }

    // Wish-Kriterium
    /**
     * @param postID: The id of the post that should be removed from the server
     */
    fun removePost(postID: Long) {
        // TODO: Implement Method

        return;
    }

    // -----------------------------ProjectParticipantsController-------------------------------
    /**
     * @param userToAdd: The id of the user that should be added to the project
     * @param projectID: The id of the project to which the user is to be added
     */
    fun addUser(userToAdd: String, projectID: Long): Boolean{
        // TODO: Implement Method

        return false;
    }

    /**
     * @param userToRemove: The id of the user that sould be removed from the project
     * @param projectID: The id of the project from which the user should be removed
     */
    fun removeUser(userToRemove: String, projectID: Long): Boolean{
        // TODO: Implement Method

        return false;
    }

    /**
     * @return LONG: Returns the id of the created project. Returns -1 if an error occured
     */
    fun addProject(): Long{
        // TODO: Implement Method

        return -1;
    }

    // -----------------------------DeltaController-------------------------------
    /**
     * @param projectID: The id of the project to which the project command should be added
     * @param projectCommands: The project commands that should be send to the server (as JSON)
     * @return Collection<String>: The successfully uploaded project commands (as JSONs) // TODO: ändere dies im Entwurfsdokument
     */
    fun sendCommandsToServer(projectID: Long, projectCommands: Collection<String>): Collection<String> {
        // TODO: Implement Method

        return mutableListOf("");
    }

    /**
     * @param projectID: The id of the project whose deltas (fetchRequests) you want to load into the FetchRequestQueue
     */
    fun getDeltasFromServer(projectID: Long): Unit {
        // TODO: Implement Method

        return
    }

    /**
     * @param projectCommand: The projectCommand that should be uploaded to the server (as JSON)
     * @param forUser: The id of the user whose fetch request is answered
     * @param initialAddedDate: // TODO
     * @param projectID: The id of the project belonging to the project command
     * @param wasAdmin: Was the user a project administrator when the command was created
     */
    fun provideOldData(projectCommand: String, forUser: String, initialAddedDate: LocalDateTime, initialAddedBy: String, projectID: Long, wasAdmin: Boolean) {
        // TODO: Implement Method

        return;
    }

    /**
     * @param LocalDateTime: The time how long an project command can remain on the server until it gets deleted by the server
     */
    fun getRemoveTime(): LocalDateTime {
        // TODO: Implement Method0

        return LocalDateTime.parse("0001-01-01T00:00")
    }

    // -----------------------------FetchRequestController-------------------------------
    /**
     * @param projectID: The id of the project to which the fetch request should be uploaded
     * @param requestInfo: The fetch request as JSON
     */
    fun demandOldData(projectID: Long, requestInfo: String) {
        // TODO: Implement Method

        return;
    }

    /**
     * @param projectID: The id of the project from which the fetch requests should be downloaded
     */
    fun getFetchRequests(projectID: Long){ // TODO: Ausgabe ist im Entwurfsheft Collection<FetchRequest>
        // TODO: Implement Method

        return;
    }

    // -----------------------------ObserverLogic-------------------------------
    /**
     * @param observer: The observer that should be added to the FetchRequestQueue
     */
    fun addObserverToFetchRequestQueue(observer: FetchRequestQueueObserver) {
        serverManager.addObserverToFetchRequestQueue(observer)
    }

    /**
     * @param observer: The observer that should be removed from the FetchRequestQueue
     */
    fun unregisterObserverFromFetchRequestQueue(observer: FetchRequestQueueObserver) {
        serverManager.unregisterObserverFromFetchRequestQueue(observer)
    }

    /**
     * @return INT: The length of the FetchRequestQueue
     */
    fun getFetchRequestQueueLength(): Int {
        return serverManager.getFetchRequestQueueLength()
    }

    /**
     * @param observer: The observer that should be added to the ProjectCommandQueue
     */
    fun addObserverToProjectCommandQueue(observer: ProjectCommandQueueObserver) {
        serverManager.addObserverToProjectCommandQueue(observer)
    }

    /**
     * @param observer: The observer that should be removed from the ProjectCommandQueue
     */
    fun unregisterObserverFromProjectCommandQueue(observer: ProjectCommandQueueObserver) {
        serverManager.unregisterObserverFromProjectCommandQueue(observer)
    }

    /**
     * @return INT: The length of the ProjectCommandQueue
     */
    fun getProjectCommandQueueLength(): Int {
        return serverManager.getProjectCommandQueueLength()
    }
}