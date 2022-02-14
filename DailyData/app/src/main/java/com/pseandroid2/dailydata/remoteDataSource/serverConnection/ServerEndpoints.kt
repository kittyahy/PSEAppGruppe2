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

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.AddPostParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.ProvideOldDataParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.Delta
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.FetchRequest
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.PostPreview
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.TemplateDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Declares all possible REST server Responses
 */
interface ServerEndpoints {

    // Greeting Controller -----------------------------------------------------------------------
    /**
     * Returns "Hello", to make sure the server is available
     *
     * @return Greeting to signalise, the server is reachable
     */
    @GET("greet")
    suspend fun greet(): Response<String>

    // This method is for testing only
    /**
     * Returns all uploaded posts from a user
     *
     * @param token: The authentication token
     * @return The postIDs of the uploaded posts
     */
    @GET("test/allPosts")
    suspend fun getPostsFromUser(@Header("token") token: String): Response<List<Int>>

    /**
     * Clears all saved fetchRequsts, deltas and posts from the server
     */
    @DELETE("test/deleteAll")
    suspend fun clearServer(): Response<Boolean>

    // Post Controller ---------------------------------------------------------------------------
    /**
     * Provides all PostsPreviews (title and image) with their PostIds
     *
     * @param token: The authentication token
     * @return A list with PostPreview, which contains postPreviews and the Post ids
     */
    @GET("Posts" + "/allPreview")
    suspend fun getAllPostPreview(@Header("token") token: String): Response<List<PostPreview>>

    /**
     * Provides all Templates details from fromPost. Returns the identifier and the DetailView (image and title) from
     * a template.
     * For every template is declared if it's a project template or not
     *
     * @param token:    The authentication token
     * @param fromPost: Declares from which post the postDetail is recommended
     * @return A list of the template Details
     */
    @GET("Posts" + "/detail/{post}")
    suspend fun getPostDetail(
        @Header("token") token: String,
        @Path("post") fromPost: Int
    ): Response<List<TemplateDetail>>

    /**
     * Provides the projectTemplate from the post fromPost
     *
     * @param token:    The authentication token
     * @param fromPost: Declares from which post the projectTemplate is recommended (provided by the client)
     * @return The projectTemplate as JSON
     */
    @GET("Posts" + "/{post}/projectTemplate")
    suspend fun getProjectTemplate(
        @Header("token") token: String,
        @Path("post") fromPost: Int
    ): Response<String>

    /**
     * Provides a specified GraphTemplate from a specified post
     *
     * @param token:          The authentication token
     * @param fromPost:       Declares from which project the graph template is recommended
     * @param templateNumber: Declares which template is recommended
     * @return The GraphTemplate as JSON
     */
    @GET("Posts" + "/{post}/{template}")
    suspend fun getGraphTemplate(
        @Header("token") token: String, @Path("post") fromPost: Int,
        @Path("template") templateNumber: Int
    ): Response<String>

    /**
     * Adds a new Post. If there is more than allowed from the same user, they can not create a new one
     *
     * @param token:  The authentication token
     * @param params: The data, which are recommended to add a new post. {@link AddPostParameter} specifies
     *                   this parameter
     * @return The postID of the new post or 0 if the user has too many posts and can't add a new one
     */
    @POST("Posts" + "/add")
    suspend fun addPost(
        @Header("token") token: String,
        @Body params: AddPostParameter
    ): Response<Int>

    /**
     * Removes a post if the user is allowed to remove it
     *
     * @param token:    The authentication token
     * @param postID:   Which Post should be removed
     * @return if the post could be removed
     */
    @DELETE("Posts" + "/remove/{post}")
    suspend fun removePost(
        @Header("token") token: String,
        @Path("post") postID: Int
    ): Response<Boolean>


    // ProjectParticipantsController ---------------------------------------------------------------------------
    /**
     * Adds a user to a project, if it's possible. If a user tries to join a project, where they already participate,
     * they also get the initial for the project
     *
     * @param token:    The authentication token
     * @param projectId The project, where the user wants to participate
     * @return The initial for the project, if the user participates now in the project, if not, it returns an empty
     * String ("")
     */
    @POST("OnlineDatabase" + "/addUser/{id}")
    suspend fun addUser(
        @Header("token") token: String,
        @Path("id") projectId: Long
    ): Response<String>

    /**
     * Removes a user from a project. A user can remove himself.
     * The method also checks if the user is an admin, if he wants to remove another person from the project.
     * If the user is the last user in the project, and they can remove themselves, the project gets deleted eminently
     *
     * @param token:    The authentication token
     * @param projectId    The project, where a user should be removed from
     * @param userToRemove All information, which are necessary to remove a user from a project
     * @return True, if the user is removed, false if it was not possible
     */
    @DELETE("OnlineDatabase" + "/removeUser/{id}")
    suspend fun removeUser(
        @Header("token") token: String, @Path("id") projectId: Long,
        @Query("userToRemove") userToRemove: String
    ): Response<Boolean>

    /**
     * Adds a new project. The user, who initializes the project is the admin
     *
     * @param token:            The authentication token
     * @param projectDetails:   The initial for the project, which are needed for the client
     * @return The projectId for the new project
     */
    @POST("OnlineDatabase" + "/newProject")
    suspend fun addProject(
        @Header("token") token: String,
        @Body projectDetails: String
    ): Response<Long>

    /**
     * Returns all user of the project, which currently participate.
     *
     * @param token:        The authentication token
     * @param projectId:    The id, from which project the participants were recommended.
     * @return A list with users.
     */
    @GET("OnlineDatabase" + "/{id}/participants")
    suspend fun getParticipants(
        @Header("token") token: String,
        @Path("id") projectId: Long
    ): Response<List<String>>

    /**
     * Gets the project admin
     *
     * @param token:        The authentication token
     * @param projectId:    The id from which the admin is recommended
     * @return The userID of the project admin
     */
    @GET("OnlineDatabase" + "/{id}/admin")
    suspend fun getAdmin(
        @Header("token") token: String,
        @Path("id") projectId: Long
    ): Response<String>

    // DeltaController -----------------------------------------------------------------------------
    /**
     * Creates a new delta for a project to save one command.
     *
     * @param token:        The authentication token
     * @param projectId:    Declares to which projects the command belongs
     * @param command:      The command, which should be saved
     * @return True, if the delta could be saved. false if not
     */
    @POST("OnlineDatabase/Delta" + "/save/{id}")
    suspend fun saveDelta(
        @Header("token") token: String, @Path("id") projectId: Long,
        @Body command: String
    ): Response<Boolean>

    /**
     * Provides all new Deltas which belongs to a project, which the user don't have, and all old deltas which
     * belongs to the user
     *
     * @param token:        The authentication token
     * @param projectId:    Declares from which project the delta is recommended (provided in the URL by the client)
     * @return a list of recommended Deltas
     */
    @GET("OnlineDatabase/Delta" + "/get/{id}")
    suspend fun getDelta(
        @Header("token") token: String,
        @Path("id") projectId: Long
    ): Response<List<Delta>>

    /**
     * Recreates an old Delta, for a defined person and project
     *
     * @param token:        The authentication token
     * @param projectId:    To which project the delta belongs
     * @param params:       The data which are recommended to save an old delta. {@link ProvideOldDataParameter}
     *                          specifies all parameters
     */
    @POST("OnlineDatabase/Delta" + "/provide/{id}")
    suspend fun provideOldData(
        @Header("token") token: String, @Path(value = "id") projectId: Long,
        @Body params: ProvideOldDataParameter
    ): Response<Boolean>

    /**
     * Returns the period length, after which a new delta gets deleted (in Minutes)
     *
     * @param token: The authentication token
     * @return period length, after which a delta gets deleted.
     */
    @GET("OnlineDatabase/Delta" + "/time")
    suspend fun getRemoveTime(@Header("token") token: String): Response<Long>


    // FetchRequestController --------------------------------------------------------------------
    /**
     * Saves a request for old Data by the client.
     * Another participant of the same can fetch such requests
     *
     * @param token:      The authentication token
     * @param projectID   The project to which the request belongs (provided by the client)
     * @param requestInfo All information, which are necessary to save a fetchRequest.  specifies all parameters
     */
    @POST("OnlineDatabase/request" + "/need/{id}")
    suspend fun demandOldData(
        @Header("token") token: String, @Path("id") projectID: Long,
        @Body requestInfo: String
    ): Response<Boolean>

    /**
     * Provides all {@link FetchRequest}, which belongs to the project
     *
     * @param token:        The authentication token
     * @param projectId:    The project, to which the requests belong (provided by the client)
     * @return A list of {@link FetchRequest}
     */
    @GET("OnlineDatabase/request" + "/provide/{id}")
    suspend fun getFetchRequest(
        @Header("token") token: String,
        @Path("id") projectId: Long
    ): Response<Collection<FetchRequest>>
}