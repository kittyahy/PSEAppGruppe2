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

package com.pseandroid2.dailydata.remoteDataSource.serverConnection

import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueue
import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueueObserver
import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandInfo
import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueue
import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueueObserver
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.Delta
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.FetchRequest
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.PostPreview
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.TemplateDetail
import com.pseandroid2.dailydata.util.ui.Template

import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

import java.time.LocalDateTime
import javax.inject.Inject

class ServerManager @Inject constructor(restapi: RESTAPI) {
    private val restAPI: RESTAPI = restapi

    private val fetchRequestQueue: FetchRequestQueue = FetchRequestQueue()
    private val projectCommandQueue: ProjectCommandQueue = ProjectCommandQueue()


    // ------------------------------ServerLogic--------------------------------
    // ----------------------------------GreetingController--------------------------------
    /**
     * @return Boolean: If a server connection possible return true, else return false
     */
    fun greet(): Boolean {
        return restAPI.greet()
    }

    // ----------------------------------PostsController-------------------------------
    /**
     * @param authToken: The authentication token
     * @return Collection<PostPreview>: The previews of the posts
     */
    fun getAllPostPreview(authToken: String): Collection<PostPreview> {
        return restAPI.getAllPostsPreview(authToken)
    }

    /**
     * @param fromPost: The id from the searched post
     * @param authToken: The authentication token
     * @return Collection<TemplateDetail>: Returns the detailed post belonging to the post id
     */
    fun getPostDetail(fromPost: Int, authToken: String): Collection<TemplateDetail> {
        return restAPI.getPostDetail(fromPost, authToken)
    }

    /**
     * @param fromPost: The post from which the project template should be downloaded
     * @param authToken: The authentication token
     * @return String - The requested project template as JSON
     */
    fun getProjectTemplate(fromPost: Int, authToken: String): String {
        return restAPI.getProjectTemplate(fromPost, authToken)
    }

    /** Downloads one graph template that is contained by a post
     *
     * @param fromPost: The post from which the graph templates should be downloaded
     * @param templateNumber: Which graph template should be downloaded from the post
     * @param authToken: The authentication token
     * @return String - The requested graph template as JSON
     */
    fun getGraphTemplate(fromPost: Int, templateNumber: Int, authToken: String): String {
        return restAPI.getGraphTemplate(fromPost, templateNumber, authToken)
    }

    // Wish-criteria
    /**
     * @param postPreview: The preview of the post that should be added
     * @param projectTemplate: The project template that belongs to the post as JSON
     * @param Collection<String>: The graph templates that belong to the post as JSON
     * @param authToken: The authentication token
     * @return Int: The PostID of the new post. -1 if the call didn't succeed
     */
    fun addPost(postPreview: String, projectTemplate: String, graphTemplate: Collection<String>, authToken: String): Int {
        return restAPI.addPost(postPreview, projectTemplate, graphTemplate, authToken)
    }

    // Wish-criteria
    /**
     * @param postID: The id of the post that should be removed from the server
     * @param authToken: The authentication token
     * @return Boolean: Did the server call succeed
     */
    fun removePost(postID: Int, authToken: String): Boolean {
        return restAPI.removePost(postID, authToken)
    }

    // ----------------------------------ProjectParticipantsController-------------------------------
    /**
     * @param projectID: The id of the project to which the user is to be added
     * @param authToken: The authentication token
     * @return Did the task succeed?
     */
    fun addUser(projectID: Long, authToken: String): Boolean{
        return restAPI.addUser(projectID, authToken)
    }

    /**
     * @param userToRemove: The id of the user that sould be removed from the project
     * @param projectID: The id of the project from which the user should be removed
     * @param authToken: The authentication token
     * @return Did the task succeed?
     */
    fun removeUser(userToRemove: String, projectID: Long, authToken: String): Boolean{
        return restAPI.removeUser(userToRemove, projectID, authToken)
    }

    /**
     * @param authToken: The authentication token
     * @return LONG: Returns the id of the created project. Returns -1 if an error occured
     */
    fun addProject(authToken: String): Long{
        return restAPI.addProject(authToken)
    }

    // ----------------------------------DeltaController-------------------------------
    /**
     * @param projectID: The id of the project to which the project commands should be uploaded
     * @param projectCommands: The project commands that should be send to the server (as JSON)
     * @param authToken: The authentication token
     * @return Collection<String>: The successfully uploaded project commands (as JSONs) // TODO: ändere dies im Entwurfsdokument
     */
    fun sendCommandsToServer(projectID: Long, projectCommands: Collection<String>, authToken: String): Collection<String> {
        val successfullyUploaded: MutableList<String> = mutableListOf()
        //TODO: Write proper tests for this

        val jobs: MutableList<Job> = mutableListOf<Job>()
        runBlocking { // this: CoroutineScope TODO: Test if this is ok
            projectCommands.forEach { // Calls saveDelta in parallel
                // Send each project command in parallel
                val job = launch {
                    val uploadedSuccessfully: Boolean = restAPI.saveDelta(projectID, it, authToken)

                    if (uploadedSuccessfully) {
                        successfullyUploaded.add(it)
                    }
                }
                jobs.add(job)
            }
            jobs.joinAll()
        }

        return successfullyUploaded
    }

    /**
     * @param projectID: The id of the project whose deltas (projectCommands) you want to load into the FetchRequestQueue
     * @param authToken: The authentication token
     */
    fun getDeltasFromServer(projectID: Long, authToken: String): Unit {
        val receivedProjectCommands: Collection<Delta> = restAPI.getDelta(projectID, authToken)
        receivedProjectCommands.forEach {
            // Transform Delta into an Project Command
            val queueElement: ProjectCommandInfo = ProjectCommandInfo(wentOnline = it.addedToServer,
                commandByUser =  it.user, isProjectAdmin = it.isAdmin, projectCommand = it.projectCommand)
            projectCommandQueue.addProjectCommand(queueElement)
        }
    }

    /**
     * @param projectCommand: The projectCommand that should be uploaded to the server (as JSON)
     * @param forUser: The id of the user whose fetch request is answered
     * @param initialAddedDate: The time when the fetchRequest is uploaded
     * @param projectID: The id of the project belonging to the project command
     * @param wasAdmin: Was the user a project administrator when the command was created
     * @param authToken: The authentication token
     * @return Boolean: Did the server call succeed
     */
    fun provideOldData(projectCommand: String, forUser: String, initialAddedDate: LocalDateTime, initialAddedBy: String, projectID: Long, wasAdmin: Boolean, authToken: String): Boolean {
        return restAPI.providedOldData(projectCommand, forUser, initialAddedDate, initialAddedBy, projectID, wasAdmin, authToken)
    }

    /**
     * @param authToken: The authentication token
     * @return LocalDateTime: The time how long an project command can remain on the server until it gets deleted by the server
     */
    fun getRemoveTime(authToken: String): LocalDateTime {
        return restAPI.getRemoveTime(authToken)
    }

    // ----------------------------------FetchRequestController-------------------------------
    /**
     * @param projectID: The id of the project to which the fetch request should be uploaded
     * @param requestInfo: The fetch request as JSON
     * @param authToken: The authentication token
     * @return Boolean: Did the server call succeed
     */
    fun demandOldData(projectID: Long, requestInfo: String, authToken: String): Boolean {
        return restAPI.demandOldData(projectID, requestInfo, authToken)
    }

    /**
     * @param projectID: The id of the project from which the fetch requests should be downloaded
     * @param authToken: The authentication token
     */
    fun getFetchRequests(projectID: Long, authToken: String) { // TODO: Ausgabe ist im Entwurfsheft Collection<FetchRequest>
        val receivedFetchRequests: Collection<FetchRequest> = restAPI.getFetchRequests(projectID, authToken)
        receivedFetchRequests.forEach {
            fetchRequestQueue.addFetchRequest(it)
        }
    }

    // -----------------------------ObserverLogic-------------------------------
    /**
     * @param observer: The observer that should be added to the FetchRequestQueue
     */
    fun addObserverToFetchRequestQueue(observer: FetchRequestQueueObserver) {
        fetchRequestQueue.registerObserver(observer)
    }

    /**
     * @param observer: The observer that should be removed from the FetchRequestQueue
     */
    fun unregisterObserverFromFetchRequestQueue(observer: FetchRequestQueueObserver) {
        fetchRequestQueue.unregisterObserver(observer)
    }

    /**
     * @param observer: The observer that should be added to the ProjectCommandQueue
     */
    fun addObserverToProjectCommandQueue(observer: ProjectCommandQueueObserver) {
        projectCommandQueue.registerObserver(observer)
    }

    /**
     * @param observer: The observer that should be removed from the ProjectCommandQueue
     */
    fun unregisterObserverFromProjectCommandQueue(observer: ProjectCommandQueueObserver) {
        projectCommandQueue.unregisterObserver(observer)
    }

    // QueueLogic
    /**
     * @return Int: The length of the FetchRequestQueue
     */
    fun getFetchRequestQueueLength(): Int {
        return fetchRequestQueue.getQueueLength()
    }

    /**
     * @return String: Returns a fetchRequest as JSON if there is one in the queue. (Returns null if the queue is empty)
     */
    fun getFetchRequestFromQueue(): FetchRequest? {
        return fetchRequestQueue.getFetchRequest()
    }

    /**
     * @return INT: The length of the ProjectCommandQueue
     */
    fun getProjectCommandQueueLength(): Int {
        return projectCommandQueue.getQueueLength()
    }

    /**
     * @return ProjectCommandInfo: Returns a projectCommand as a ProjectCommandInfo Object if there is one in the queue. (Returns null if the queue is empty)
     */
    fun getProjectCommandFromQueue(): ProjectCommandInfo? {
        return projectCommandQueue.getProjectCommand()
    }
}