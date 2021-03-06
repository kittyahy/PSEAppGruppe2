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

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueue
import com.pseandroid2.dailydata.remoteDataSource.queue.FetchRequestQueueObserver
import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandInfo
import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueue
import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandQueueObserver
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.forRepoReturns.PostPreviewWithPicture
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.forRepoReturns.TemplateDetailWithPicture
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.PostPreviewWrapper
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.TemplateDetailWrapper
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.Delta
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.FetchRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Manages all features which are related to our server
 */
class ServerManager @Inject constructor(restapi: RESTAPI) {
    private val restAPI: RESTAPI = restapi

    private val fetchRequestQueue: FetchRequestQueue = FetchRequestQueue()
    private val projectCommandQueue: ProjectCommandQueue = ProjectCommandQueue()

    // ------------------------------ServerLogic--------------------------------
    // ----------------------------------GreetingController--------------------------------
    /**
     * Checks if it possible to connect to our server
     *
     * @return Boolean: If a server connection possible return true, else return false
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun connectionToServerPossible(): Boolean {
        return restAPI.greet()
    }

    // ----------------------------------PostsController-------------------------------
    /**
     * Gets post previews from the server
     *
     * @param authToken: The authentication token
     * @return Collection<PostPreviewWithPicture>: The previews of the posts
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getAllPostPreview(authToken: String): Collection<PostPreviewWithPicture> {
        //convert PostPreview from PostPreviewWithPicture
        val postPreviews = restAPI.getAllPostsPreview(authToken)
        val postPreviewWithPicture = mutableListOf<PostPreviewWithPicture>()
        postPreviews.forEach {
            postPreviewWithPicture.add(
                PostPreviewWithPicture(
                    it.id,
                    it.preview,
                    byteArrayToBitmap(it.previewPicture)
                )
            )
        }
        return postPreviewWithPicture
    }

    /**
     * Gets the details of a post
     *
     * @param fromPost:     The id from the searched post
     * @param authToken:    The authentication token
     * @return Collection<TemplateDetail>: Returns the detailed post belonging to the post id
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getPostDetail(
        fromPost: Int,
        authToken: String
    ): Collection<TemplateDetailWithPicture> {
        //convert TemplateDetail to TemplateDetailWithPicture
        val templateDetails = restAPI.getPostDetail(fromPost, authToken)
        val templateDetailsWithPicture = mutableListOf<TemplateDetailWithPicture>()
        templateDetails.forEach {
            templateDetailsWithPicture.add(
                TemplateDetailWithPicture(
                    it.id,
                    it.title,
                    byteArrayToBitmap(it.detailImage),
                    it.projectTemplate
                )
            )
        }
        return templateDetailsWithPicture
    }

    /**
     * Gets the project template of a post
     *
     * @param fromPost:     The post from which the project template should be downloaded
     * @param authToken:    The authentication token
     * @return String - The requested project template as JSON
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getProjectTemplate(fromPost: Int, authToken: String): String {
        return restAPI.getProjectTemplate(fromPost, authToken)
    }

    /**
     * Downloads one graph template that is contained by a post
     *
     * @param fromPost:         The post from which the graph templates should be downloaded
     * @param templateNumber:   Which graph template should be downloaded from the post
     * @param authToken:        The authentication token
     * @return String - The requested graph template as JSON
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getGraphTemplate(fromPost: Int, templateNumber: Int, authToken: String): String {
        return restAPI.getGraphTemplate(fromPost, templateNumber, authToken)
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
    suspend fun addPost(
        postPreview: Pair<Bitmap, String>,
        projectTemplate: Pair<String, Pair<Bitmap, String>>,
        graphTemplates: List<Pair<String, Pair<Bitmap, String>>>,
        authToken: String
    ): Int {
        val postPreviewToUpload =
            PostPreviewWrapper(bitmapToByteArray(postPreview.first), postPreview.second)
        val templateDetailToUpload = Pair(
            projectTemplate.first,
            TemplateDetailWrapper(
                bitmapToByteArray(projectTemplate.second.first),
                projectTemplate.second.second
            )
        )
        val graphDetailsToUpload = mutableListOf<Pair<String, TemplateDetailWrapper>>()
        graphTemplates.forEach {
            val templateDetailWrapper =
                TemplateDetailWrapper(bitmapToByteArray(it.second.first), it.second.second)
            graphDetailsToUpload.add(
                Pair(
                    it.first,
                    templateDetailWrapper
                )
            )
        }
        return restAPI.addPost(
            postPreviewToUpload,
            templateDetailToUpload,
            graphDetailsToUpload,
            authToken
        )
    }

    /**
     * Converts a bitmap into a byte array
     *
     * @param toConvert:    The bitmap that should be converted
     * @return ByteArray:   The converted Bitmap
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    private fun bitmapToByteArray(toConvert: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        toConvert.compress(Bitmap.CompressFormat.PNG, 90, stream)

        return stream.toByteArray()
    }

    /**
     * Converts a byte array into a bitmap
     * @param toConvert:    The byte array that should be converted
     * @return ByteArray:   The converted byte array
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    private fun byteArrayToBitmap(toConvert: ByteArray): Bitmap {
        val emptyBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        if (toConvert.isEmpty()) {
            return emptyBitmap
        }
        return BitmapFactory.decodeByteArray(toConvert, 0, toConvert.size)
    }


    // Wish-criteria
    /**
     * Deletes a post from the server
     *
     * @param postID:       The id of the post that should be removed from the server
     * @param authToken:    The authentication token
     * @return Boolean: Did the server call succeed
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun removePost(postID: Int, authToken: String): Boolean {
        return restAPI.removePost(postID, authToken)
    }

    // ----------------------------------ProjectParticipantsController-------------------------------
    /**
     * Lets the currently signed in user join the project and returns the project information
     *
     * @param projectID: The id of the project to which the user is to be added
     * @param authToken: The authentication token
     * @return String: Returns the project information as a JSON. (Returns "" on error)
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun addUser(projectID: Long, authToken: String): String {
        return restAPI.addUser(projectID, authToken)
    }

    /**
     * Removes a user from a project
     *
     * @param userToRemove: The id of the user that should be removed from the project
     * @param projectID:    The id of the project from which the user should be removed
     * @param authToken:    The authentication token
     * @return Did the task succeed?
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun removeUser(userToRemove: String, projectID: Long, authToken: String): Boolean {
        return restAPI.removeUser(userToRemove, projectID, authToken)
    }

    /**
     * Creates a new online project on the server and returns the id
     *
     * @param authToken:        The authentication token
     * @param projectDetails:   The details of a project (project name, project description, table format, ...) as JSON
     * @return LONG: Returns the id of the created project. Returns -1 if an error occurred
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun addProject(authToken: String, projectDetails: String): Long {
        return restAPI.addProject(authToken, projectDetails)
    }

    /**
     * Gets all the project members
     *
     * @param authToken: The authentication token
     * @param projectID: The id of the project whose user should be returned
     * @return Collection<String>: The participants of the project. Returns empty list on error
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getProjectParticipants(authToken: String, projectID: Long): Collection<String> {
        return restAPI.getProjectParticipants(authToken, projectID)
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
        val projectParticipants = restAPI.getProjectParticipants(authToken, projectID)
        projectParticipants.forEach {
            if (it == userID) {
                return true
            }
        }
        return false
    }

    /**
     * Gets the admin of the project
     *
     * @param authToken: The authentication token
     * @param projectID: The id of the project whose admin should be returned
     * @return String: The UserID of the admin. Returns "" on error
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getProjectAdmin(authToken: String, projectID: Long): String {
        return restAPI.getProjectAdmin(authToken, projectID)
    }

    // ----------------------------------DeltaController-------------------------------
    /**
     * Sends newly added project commands to the server
     *
     * @param projectID:        The id of the project to which the project commands should be uploaded
     * @param projectCommands:  The project commands that should be send to the server (as JSON)
     * @param authToken:        The authentication token
     * @return Collection<String>: The successfully uploaded project commands (as JSONs)
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun sendCommandsToServer(
        projectID: Long,
        projectCommands: Collection<String>,
        authToken: String
    ): Collection<String> = coroutineScope {
        val deffered = async(Dispatchers.IO) {
            val successfullyUploaded: MutableList<String> = mutableListOf()

            if (projectCommands.isEmpty()) {
                return@async successfullyUploaded
            }

            val jobs: MutableList<Job> = mutableListOf()
            runBlocking { // this: CoroutineScope
                projectCommands.forEach { // Calls saveDelta in parallel
                    // Send each project command in parallel
                    val job = launch {
                        val uploadedSuccessfully: Boolean =
                            restAPI.saveDelta(projectID, it, authToken)

                        if (uploadedSuccessfully) {
                            successfullyUploaded.add(it)
                        }
                    }
                    jobs.add(job)
                }
                jobs.joinAll()
            }

            return@async successfullyUploaded
        }
        deffered.await()
    }

    /**
     * Gets the project commands of a project
     *
     * @param projectID: The id of the project whose deltas (projectCommands) you want to load into the FetchRequestQueue
     * @param authToken: The authentication token
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getProjectCommandsFromServer(projectID: Long, authToken: String) {
        val receivedProjectCommands: Collection<Delta> = restAPI.getDelta(projectID, authToken)
        receivedProjectCommands.forEach {
            // Transform Delta into an Project Command
            val queueElement = ProjectCommandInfo(
                it.project,
                commandByUser = it.user,
                isProjectAdmin = it.admin,
                projectCommand = it.projectCommand,
                it.addedToServerS,
            )
            projectCommandQueue.addProjectCommand(queueElement)
        }
    }

    /**
     * Answers a fetch request
     *
     * @param projectCommand:   The projectCommand that should be uploaded to the server (as JSON)
     * @param forUser:          The id of the user whose fetch request is answered
     * @param initialAddedDate: The time when the fetchRequest is uploaded
     * @param projectID:        The id of the project belonging to the project command
     * @param wasAdmin:         Was the user a project administrator when the command was created
     * @param authToken:        The authentication token
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
        wasAdmin: Boolean,
        authToken: String
    ): Boolean {
        return restAPI.provideOldData(
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
     * Gets the remove time from the server
     *
     * @param authToken: The authentication token
     * @return Long: The time how long an project command can remain on the server until it gets deleted by the server. On error returns -1
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getRemoveTime(authToken: String): Long {
        return restAPI.getRemoveTime(authToken)
    }

    // ----------------------------------FetchRequestController-------------------------------
    /**
     * Sends a fetch request to the server
     *
     * @param projectID:    The id of the project to which the fetch request should be uploaded
     * @param requestInfo:  The fetch request as JSON
     * @param authToken:    The authentication token
     * @return Boolean: Did the server call succeed
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun demandOldData(projectID: Long, requestInfo: String, authToken: String): Boolean {
        return restAPI.demandOldData(projectID, requestInfo, authToken)
    }

    /**
     * Fills the FetchRequestQueue with fetch requests from a project
     *
     * @param projectID: The id of the project from which the fetch requests should be downloaded
     * @param authToken: The authentication token
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getFetchRequests(projectID: Long, authToken: String) {
        val receivedFetchRequests: Collection<FetchRequest> =
            restAPI.getFetchRequests(projectID, authToken)
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

    /** // TODO This method is just for testing
     * Deletes all Posts from User
     */
    suspend fun deleteAllPostsFromUser(authToken: String) {
        val postIds: List<Int> = restAPI.getPostsFromUser(authToken)
        postIds.forEach {
            if (it > 0) {
                restAPI.removePost(it, authToken)
            }
        }
    }
}