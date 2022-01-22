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

import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

import java.time.LocalDateTime
import javax.inject.Inject

class ServerManager @Inject constructor(private val restapi: RESTAPI) {
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
     * @return Collection<String>: The previews of the posts (as JSONs)
     */
    fun getAllPostPreview(authToken: String): Collection<String> {
        return restAPI.getAllPostsPreview(authToken)
    }

    /**
     * @param fromPost: The id from the searched post
     * @return Collection<String>: Returns the detailed post belonging to the post id
     */
    fun getPostDetail(fromPost: Int, authToken: String): Collection<String> {
        return restAPI.getPostDetail(fromPost, authToken)
    }

    /**
     * @param fromPost: The post from which the project template should be downloaded
     * @return String - The requested project template as JSON
     */
    fun getProjectTemplate(fromPost: Int, authToken: String): String {
        return restAPI.getProjectTemplate(fromPost, authToken)
    }

    /** Downloads one graph template that is contained by a post
     *
     * @param fromPost: The post from which the graph templates should be downloaded
     * @param templateNumber: Which graph template should be downloaded from the post
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
     * @return Boolean: Did the server call succeed
     */
    fun addPost(postPreview: String, projectTemplate: String, graphTemplate: Collection<String>, authToken: String): Boolean {
        return restAPI.addPost(postPreview, projectTemplate, graphTemplate, authToken)
    }

    // Wish-criteria
    /**
     * @param postID: The id of the post that should be removed from the server
     * @return Boolean: Did the server call succeed
     */
    fun removePost(postID: Int, authToken: String): Boolean {
        return restAPI.removePost(postID, authToken)
    }

    // ----------------------------------ProjectParticipantsController-------------------------------
    /**
     * @param userToAdd: The id of the user that should be added to the project
     * @param projectID: The id of the project to which the user is to be added
     */
    fun addUser(userToAdd: String, projectID: Long, authToken: String): Boolean{
        return restAPI.addUser(projectID, authToken)
    }

    /**
     * @param userToRemove: The id of the user that sould be removed from the project
     * @param projectID: The id of the project from which the user should be removed
     */
    fun removeUser(userToRemove: String, projectID: Long, authToken: String): Boolean{
        return restAPI.removeUser(userToRemove, projectID, authToken)
    }

    /**
     * @return LONG: Returns the id of the created project. Returns -1 if an error occured
     */
    fun addProject(authToken: String): Long{
        return restAPI.addProject(authToken)
    }

    // ----------------------------------DeltaController-------------------------------
    /**
     * @param projectID: The id of the project to which the project command should be added
     * @param projectCommands: The project commands that should be send to the server (as JSON)
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
     * @param projectID: The id of the project whose deltas (fetchRequests) you want to load into the FetchRequestQueue
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
     * @param initialAddedDate: // TODO
     * @param projectID: The id of the project belonging to the project command
     * @param wasAdmin: Was the user a project administrator when the command was created
     * @return Boolean: Did the server call succeed
     */
    fun provideOldData(projectCommand: String, forUser: String, initialAddedDate: LocalDateTime, initialAddedBy: String, projectID: Long, wasAdmin: Boolean, authToken: String): Boolean {
        return restAPI.providedOldData(projectCommand, forUser, initialAddedDate, initialAddedBy, projectID, wasAdmin, authToken)
    }

    /**
     * @param LocalDateTime: The time how long an project command can remain on the server until it gets deleted by the server
     */
    fun getRemoveTime(authToken: String): LocalDateTime {
        return restAPI.getRemoveTime(authToken)
    }

    // ----------------------------------FetchRequestController-------------------------------
    /**
     * @param projectID: The id of the project to which the fetch request should be uploaded
     * @param requestInfo: The fetch request as JSON
     * @return Boolean: Did the server call succeed
     */
    fun demandOldData(projectID: Long, requestInfo: String, authToken: String): Boolean {
        return restAPI.demandOldData(projectID, requestInfo, authToken)
    }

    /**
     * @param projectID: The id of the project from which the fetch requests should be downloaded
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
     * @return INT: The length of the FetchRequestQueue
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