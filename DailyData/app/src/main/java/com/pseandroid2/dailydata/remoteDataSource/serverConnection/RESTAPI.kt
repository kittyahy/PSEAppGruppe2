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
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.AddPostParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.DemandOldDataParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.ProvideOldDataParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.RemoveUserParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.SaveDeltaParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.Delta
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.FetchRequest
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDateTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

import com.google.gson.Gson
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.PostPreview
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.TemplateDetail


class RESTAPI {
    private var baseUrl: String = "http://261ee33a-ba27-4828-b5df-f5f8718defe8.ka.bw-cloud-instance.org:8080" // The URL from our server

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val server: ServerEndpoints = retrofit.create(ServerEndpoints::class.java)



    // Note: There is no need to send the User ID of the current user to the server, as this can be read from the Firebase authToken

    // TODO: Bei alles Tests prüfen wie man nach Fehlern sucht

    //------------------------------------- Greetings Controller -------------------------------------
    /**
     * @return Boolean: Returns true, if the server could be successfully reached. Otherwise return false
     */
    fun greet(): Boolean {
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
     * @param authToken: The authentication token
     * @return Collection<PostPreview>: The previews of the posts
     */
    fun getAllPostsPreview(authToken: String): Collection<PostPreview> {
        val call: Call<List<PostPreview>> = server.getAllPostPreview(authToken)

        return call.execute().body() ?: emptyList()
    }

    /**
     * @param fromPost: The id from the searched post
     * @param authToken: The authentication token
     * @return Collection<TemplateDetail>: Returns the detailed post belonging to the post id
     */
    fun getPostDetail(fromPost: Int, authToken: String): Collection<TemplateDetail> {
        val call: Call<List<TemplateDetail>> = server.getPostDetail(authToken, fromPost)

        return call.execute().body() ?: emptyList()
    }

    /**
     * @param fromPost: The post from which the project template should be downloaded
     * @param authToken: The authentication token
     * @return String - The requested project template as JSON
     */
    fun getProjectTemplate(fromPost: Int, authToken: String): String {
        val call: Call<String> = server.getProjectTemplate(authToken, fromPost)

        return call.execute().body() ?: ""
    }

    /** Downloads one graph template that is contained by a post
     *
     * @param fromPost: The post from which the graph templates should be downloaded
     * @param templateNumber: Which graph template should be downloaded from the post
     * @param authToken: The authentication token
     * @return String - The requested graph template as JSON
     */
    fun getGraphTemplate(fromPost: Int, templateNumber: Int, authToken: String): String {
        val call: Call<String> = server.getGraphTemplate(authToken, fromPost, templateNumber)

        return call.execute().body() ?: ""
    }

    // Wish-criteria
    /**
     * @param postPreview: The preview of the post that should be added
     * @param projectTemplate: The project template that belongs to the post as JSON
     * @param Collection<String>: The graph templates that belong to the post as JSON
     * @param authToken: The authentication token
     * @return Int: The PostID of the new post. -1 if the call didn't succeed
     */// TODO Überarbeite JAVADOC
    fun addPost (postPreview: String, projectTemplate: Pair<String, String>, graphTemplate: Collection<Pair<String, String>>, authToken: String): Int {
        val params: AddPostParameter = AddPostParameter(postPreview, projectTemplate, graphTemplate)
        Log.d("addPost", gson.toJson(params))
        val call: Call<Int> = server.addPost(authToken, params)

        return call.execute().body() ?: -1
    }

    // Wish-criteria
    /**
     * @param postID: The id of the post that should be removed from the server
     * @param authToken: The authentication token
     * @return Boolean: Did the server call succeed
     */
    fun removePost (postID: Int, authToken: String): Boolean {
        val call: Call<Boolean> = server.removePost(authToken, postID)

        return call.execute().body() ?: false
    }


    //------------------------------------- ProjectParticipantsController -------------------------------------
    /**
     * @param projectID: The id of the project to which the user is to be added
     * @param authToken: The authentication token
     * @return Boolean: Did the server call succeed
     */
    fun addUser(projectId: Long, authToken: String): Boolean {
        val call: Call<Boolean> = server.addUser(authToken, projectId)

        return call.execute().body() ?: false
    }

    /**
     * @param userToRemove: The id of the user that sould be removed from the project
     * @param projectID: The id of the project from which the user should be removed
     * @param authToken: The authentication token
     * @return Boolean: Did the server call succeed
     */
    fun removeUser(userToRemove: String, projectID: Long, authToken: String): Boolean {
        val params: RemoveUserParameter = RemoveUserParameter(userToRemove = userToRemove, projectID = projectID)

        val call: Call<Boolean> = server.removeUser(authToken, projectID, params)

        return call.execute().body() ?: false
    }

    /**
     * @param authToken: The authentication token
     * @return LONG: Returns the id of the created project. Returns -1 if an error occured
     */
    fun addProject(authToken: String): Long {
        val call: Call<Long> = server.addProject(authToken)

        return call.execute().body() ?: -1
    }


    //------------------------------------- Delta Controller -------------------------------------
    /** Uploads a Project Command to the Server
     * @param projectID: The id of the project to which the project command should be uploaded
     * @param projectCommand: The project command that should be send to the server (as JSON)
     * @param authToken: The authentication token
     * @return Boolean: True if uploaded successfully, otherwise false
     */
    suspend fun saveDelta(projectID: Long, projectCommand: String, authToken: String): Boolean {
        val params: SaveDeltaParameter = SaveDeltaParameter(projectCommand)

        val call: Call<Boolean> = server.saveDelta(authToken, projectID, params)

        return call.execute().body() ?: false
    }

    /**
     * @param projectID: The id of the project whose delta (projectCommands) you want to load into the FetchRequestQueue
     * @param authToken: The authentication token
     */
    fun getDelta(projectID: Long, authToken: String): Collection<Delta> {
        val call: Call<Collection<Delta>> = server.getDelta(authToken, projectID)

        return call.execute().body() ?: mutableListOf()
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
    fun providedOldData(projectCommand: String, forUser: String, initialAdded: LocalDateTime, initialAddedBy: String, projectID: Long, wasAdmin: Boolean, authToken: String): Boolean {
        val params: ProvideOldDataParameter = ProvideOldDataParameter(projectCommand, forUser, initialAdded, initialAddedBy, wasAdmin)

        val call: Call<Boolean> = server.provideOldData(authToken, projectID, params)

        return call.execute().body() ?: false
    }

    /**
     * @param authToken: The authentication token
     * @return LocalDateTime: the requested time how long comments stay on the server before they get deleted. If an error occured returns "0001-01-01T00:00" // TODO Überprüfe grammatik
     */
    fun getRemoveTime(authToken: String): LocalDateTime {
        val call: Call<LocalDateTime> = server.getRemoveTime(authToken)

        return call.execute().body() ?: LocalDateTime.parse("0001-01-01T00:00")
    }

    //------------------------------------- FetchRequestController -------------------------------------
    /**
     * @param projectID: The id of the project to which the fetch request should be uploaded
     * @param requestInfo: The fetch request as JSON
     * @param authToken: The authentication token
     * @return Boolean: Did the server call succeed
     */
    fun demandOldData(projectID: Long, requestInfo: String, authToken: String): Boolean {
        val params: DemandOldDataParameter = DemandOldDataParameter(requestInfo)

        val call: Call<Boolean> = server.demandOldData(authToken, projectID, params)

        return call.execute().body() ?: false
    }

    /**
     * @param projectID: The id of the project from which the fetch requests should be downloaded
     * @param authToken: The authentication token
     */
    fun getFetchRequests(projectID: Long, authToken: String): Collection<FetchRequest> {
        val call: Call<Collection<FetchRequest>> = server.getFetchRequest(authToken, projectID)

        return call.execute().body() ?: emptyList()
    }
}