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

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.AddPostParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.PostPreviewWrapper
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.ProvideOldDataParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.TemplateDetailWrapper
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.Delta
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.FetchRequest
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.PostPreview
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.TemplateDetail
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.time.LocalDateTime

/**
 * Carries out all calls to our server
 */
class RESTAPI {
    private var baseUrl: String =
        "http://261ee33a-ba27-4828-b5df-f5f8718defe8.ka.bw-cloud-instance.org:8080" // The URL from our server

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val server: ServerEndpoints = retrofit.create(ServerEndpoints::class.java)


    // Note: There is no need to send the User ID of the current user to the server, as this can be read from the Firebase authToken

    //------------------------------------- Greetings Controller -------------------------------------
    /**
     * Checks if it possible to connect to our server
     *
     * @return Boolean: Returns true, if the server could be successfully reached. Otherwise return false
     */
    suspend fun greet(): Boolean {
        val greetingCall: Call<String> = server.greet()

        val response: Response<String> = greetingCall.execute()
        val body: String = response.body() ?: ""

        if (body == "Hello") {
            return true
        }
        return false
    }

    //------------------------------------- Posts Controller -------------------------------------
    /**
     * Gets post previews from the server
     *
     * @param authToken: The authentication token
     * @return Collection<PostPreview>: The previews of the posts
     */
    suspend fun getAllPostsPreview(authToken: String): Collection<PostPreview> {
        val call: Call<List<PostPreview>> = server.getAllPostPreview(authToken)

        return call.execute().body() ?: emptyList()
    }

    /**
     * Gets the details of a post
     *
     * @param fromPost:     The id from the searched post
     * @param authToken:    The authentication token
     * @return Collection<TemplateDetail>: Returns the detailed post belonging to the post id
     */
    suspend fun getPostDetail(fromPost: Int, authToken: String): Collection<TemplateDetail> {
        val call: Call<List<TemplateDetail>> = server.getPostDetail(authToken, fromPost)

        return call.execute().body() ?: emptyList()
    }

    /**
     * Gets the project template of a post
     *
     * @param fromPost:     The post from which the project template should be downloaded
     * @param authToken:    The authentication token
     * @return String - The requested project template as JSON
     */
    suspend fun getProjectTemplate(fromPost: Int, authToken: String): String {
        val call: Call<String> = server.getProjectTemplate(authToken, fromPost)

        return call.execute().body() ?: ""
    }

    /**
     * Downloads one graph template that is contained by a post
     *
     * @param fromPost:         The post from which the graph templates should be downloaded
     * @param templateNumber:   Which graph template should be downloaded from the post
     * @param authToken:        The authentication token
     * @return String - The requested graph template as JSON
     */
    suspend fun getGraphTemplate(fromPost: Int, templateNumber: Int, authToken: String): String {
        val call: Call<String> = server.getGraphTemplate(authToken, fromPost, templateNumber)

        return call.execute().body() ?: ""
    }

    // Wish-criteria
    /**
     * Uploads a post to the server
     *
     * @param postPreview:      The preview of the post that should be added
     * @param projectTemplate:  The project template. The first element of the Pair is the template as a JSON, the second one is the detailView of the template
     * @param graphTemplates:   The graph templates. The first element of a Pair is the template as a JSON, the second one is the detailView of the template
     * @param authToken:        The authentication token
     * @return Int: The PostID of the new post. -1 if the call didn't succeed, 0 if the user reached his limit of uploaded posts.
     */
    suspend fun addPost(
        postPreview: PostPreviewWrapper,
        projectTemplate: Pair<String, TemplateDetailWrapper>,
        graphTemplates: List<Pair<String, TemplateDetailWrapper>>,
        authToken: String
    ): Int {
        val params = AddPostParameter(postPreview, projectTemplate, graphTemplates)
        val call: Call<Int> = server.addPost(authToken, params)

        return call.execute().body() ?: -1
    }

    // Wish-criteria
    /**
     * Deletes a post from the server
     *
     * @param postID:       The id of the post that should be removed from the server
     * @param authToken:    The authentication token
     * @return Boolean:     Did the server call succeed
     */
    suspend fun removePost(postID: Int, authToken: String): Boolean {
        val call: Call<Boolean> = server.removePost(authToken, postID)

        return call.execute().body() ?: false
    }


    //------------------------------------- ProjectParticipantsController -------------------------------------
    /**
     * Lets the currently signed in user join the project and returns the project information
     *
     * @param projectID: The id of the project to which the user is to be added
     * @param authToken: The authentication token
     * @return String: Returns the project information as a JSON. (Returns "" on error)
     */
    suspend fun addUser(projectID: Long, authToken: String): String {
        val call: Call<String> = server.addUser(authToken, projectID)

        return call.execute().body() ?: ""
    }

    /**
     * Removes a user from a project
     *
     * @param userToRemove: The id of the user that should be removed from the project
     * @param projectID:    The id of the project from which the user should be removed
     * @param authToken:    The authentication token
     * @return Boolean: Did the server call succeed
     */
    suspend fun removeUser(userToRemove: String, projectID: Long, authToken: String): Boolean {
        val call: Call<Boolean> =
            server.removeUser(authToken, projectID, userToRemove = userToRemove)

        return call.execute().body() ?: false
    }

    /**
     * Creates a new online project on the server and returns the id
     *
     * @param authToken:        The authentication token
     * @param projectDetails:   The details of a project (project name, project description, table format, ...) as JSON
     * @return LONG: Returns the id of the created project. Returns -1 if an error occurred
     */
    suspend fun addProject(authToken: String, projectDetails: String): Long {
        val call: Call<Long> = server.addProject(authToken, projectDetails)

        return call.execute().body() ?: -1
    }

    /**
     *  Gets all the project members
     *
     *  @param authToken: The authentication token
     *  @param projectID: The id of the project whose user should be returned
     *  @return Collection<String>: The participants of the project. Returns empty list on error
     */
    suspend fun getProjectParticipants(authToken: String, projectID: Long): Collection<String> {
        val call: Call<List<String>> = server.getParticipants(authToken, projectID)

        return call.execute().body() ?: listOf()
    }

    /**
     * Gets the admin of the project
     *
     * @param authToken: The authentication token
     * @param projectID: The id of the project whose admin should be returned
     * @return String: The UserID of the admin. Returns "" on error
     */
    suspend fun getProjectAdmin(authToken: String, projectID: Long): String {
        val call: Call<String> = server.getAdmin(authToken, projectID)

        return call.execute().body() ?: ""
    }

    //------------------------------------- Delta Controller -------------------------------------
    /**
     * Uploads a Project Command to the Server
     *
     * @param projectID:        The id of the project to which the project command should be uploaded
     * @param projectCommand:   The project command that should be send to the server (as JSON)
     * @param authToken:        The authentication token
     * @return Boolean: True if uploaded successfully, otherwise false
     */
    suspend fun saveDelta(projectID: Long, projectCommand: String, authToken: String): Boolean {
        return server.saveDelta(authToken, projectID, projectCommand)
    }

    /**
     * Gets the project commands of a project
     *
     * @param projectID: The id of the project whose delta (projectCommands) you want to load into the FetchRequestQueue
     * @param authToken: The authentication token
     */
    suspend fun getDelta(projectID: Long, authToken: String): Collection<Delta> {
        val call: Call<List<Delta>> = server.getDelta(token = authToken, projectID)

        return call.execute().body() ?: return mutableListOf()
    }

    /**
     * Answers a fetch request
     *
     * @param projectCommand:   The projectCommand that should be uploaded to the server (as JSON)
     * @param forUser:          The id of the user whose fetch request is answered
     * @param initialAdded:     The time when the fetchRequest is uploaded
     * @param projectID:        The id of the project belonging to the project command
     * @param wasAdmin:         Was the user a project administrator when the command was created
     * @param authToken:        The authentication token
     * @return Boolean: Did the server call succeed
     */
    suspend fun provideOldData(
        projectCommand: String,
        forUser: String,
        initialAdded: LocalDateTime,
        initialAddedBy: String,
        projectID: Long,
        wasAdmin: Boolean,
        authToken: String
    ): Boolean {
        val params =
            ProvideOldDataParameter(command = projectCommand, forUser =  forUser, initialAdded = initialAdded.toString(),
                initialAddedBy = initialAddedBy, wasAdmin = wasAdmin)

        val call: Call<Boolean> = server.provideOldData(authToken, projectID, params)

        return call.execute().body() ?: false
    }

    /**
     * Gets the remove time from the server
     *
     * @param authToken: The authentication token
     * @return Long: The time how long an project command can remain on the server until it gets deleted by the server. On error returns -1
     */
    suspend fun getRemoveTime(authToken: String): Long {
        val call: Call<Long> = server.getRemoveTime(authToken)

        return call.execute().body() ?: -1
    }

    //------------------------------------- FetchRequestController -------------------------------------
    /**
     * Sends a fetch request to the server
     *
     * @param projectID:    The id of the project to which the fetch request should be uploaded
     * @param requestInfo:  The fetch request as JSON
     * @param authToken:    The authentication token
     * @return Boolean: Did the server call succeed
     */
    suspend fun demandOldData(projectID: Long, requestInfo: String, authToken: String): Boolean {
        val call: Call<Boolean> =
            server.demandOldData(token = authToken, projectID, requestInfo = requestInfo)

        return call.execute().body() ?: false
    }

    /**
     * Gets fetch requests from the server
     *
     * @param projectID: The id of the project from which the fetch requests should be downloaded
     * @param authToken: The authentication token
     */
    suspend fun getFetchRequests(projectID: Long, authToken: String): Collection<FetchRequest> {
        val call: Call<Collection<FetchRequest>> = server.getFetchRequest(authToken, projectID)

        return call.execute().body() ?: emptyList()
    }

    /**
     * Gets all the postIDs from the posts which one user uploaded
     * Note: This method is just for testing
     *
     * @param authToken:    The authentication token
     * @return List<Int>:   The postIDs from the posts
     */
    suspend fun getPostsFromUser(authToken: String): List<Int> {
        val call: Call<List<Int>> = server.getPostsFromUser(authToken)

        return call.execute().body() ?: emptyList()
    }
}