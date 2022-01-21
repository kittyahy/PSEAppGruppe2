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

import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandInfo
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.AddPostParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.DemandOldDataParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.ProvideOldDataParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.RemoveUserParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.RequestParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.SaveDeltaParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.Delta
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.FetchRequest
import retrofit2.Call
import java.time.LocalDateTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RESTAPI {
    private var baseUrl: String = "http://myserver.com/server/" // TODO: Die URL unseres Servers verwenden

    private var retrofit: Retrofit
    private val server: ServerEndpoints

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
            //.create(ServerEndpoints)

        server = retrofit.create(ServerEndpoints::class.java) // TODO: Wahrscheinlich ist das .java hier falsch

        // Create Services // TODO mal schauen, ob ich das brauche
        // https://square.github.io/retrofit/
        // https://dev.to/paulodhiambo/kotlin-and-retrofit-network-calls-2353
        // Synchrone Calls: https://futurestud.io/tutorials/retrofit-synchronous-and-asynchronous-requests
    }
    // Note: There is no need to send the User ID of the current user to the server, as this can be read from the Firebase authToken

    // TODO: Bei alles Tests prüfen wie man nach Fehlern sucht



    //------------------------------------- Greetings Controller -------------------------------------
    /**
     * @return Boolean: Returns true, if the server could be successfully reached. Otherwise return false
     */
    fun greet(): Boolean {
        val greetingCall: Call<String> = server.greet()

        val greeting: String = greetingCall.execute().body() ?: ""

        if (greeting == "Hello") {
            return true
        }
        return false
    }


    //------------------------------------- Posts Controller -------------------------------------
    /**
     * @return Collection<String>: The previews of the posts (as JSONs)
     */
    fun getAllPostsPreview(authToken: String): Collection<String> {
        val param: RequestParameter = RequestParameter(token = authToken)
        val call: Call<List<String>> = server.getAllPostPreview(param)

        return call.execute().body() ?: emptyList()
    }

    /**
     * @param fromPost: The id from the searched post
     * @return Collection<String>: Returns the detailed post belonging to the post id
     */
    fun getPostDetail(fromPost: Int, authToken: String): Collection<String> {
        val params: RequestParameter = RequestParameter(token = authToken)
        val call: Call<List<String>> = server.getPostDetail(fromPost, params)

        return call.execute().body() ?: emptyList()
    }

    /**
     * @param fromPost: The post from which the project template should be downloaded
     * @return String - The requested project template as JSON
     */
    fun getProjectTemplate(fromPost: Int, authToken: String): String {
        val params: RequestParameter = RequestParameter(token = authToken)
        val call: Call<String> = server.getProjectTemplate(fromPost, params)

        return call.execute().body() ?: ""
    }

    /** Downloads one graph template that is contained by a post
     *
     * @param fromPost: The post from which the graph templates should be downloaded
     * @param templateNumber: Which graph template should be downloaded from the post
     * @return String - The requested graph template as JSON
     */
    fun getGraphTemplate(fromPost: Int, templateNumber: Int, authToken: String): String {
        val params: RequestParameter = RequestParameter(token = authToken)
        val call: Call<String> = server.getGraphTemplate(fromPost, templateNumber, params)

        return call.execute().body() ?: ""
    }

    // Wish-criteria
    /**
     * @param postPreview: The preview of the post that should be added
     * @param projectTemplate: The project template that belongs to the post as JSON
     * @param Collection<String>: The graph templates that belong to the post as JSON
     * @return Boolean: Did the server call succeed
     */
    fun addPost (postPreview: String, projectTemplate: String, graphTemplate: Collection<String>, authToken: String): Boolean {
        val params: AddPostParameter = AddPostParameter(authToken, postPreview, projectTemplate, graphTemplate)
        val call: Call<Boolean> = server.addPost(params)

        return call.execute().body() ?: false
    }

    // Wish-criteria
    /**
     * @param postID: The id of the post that should be removed from the server
     * @return Boolean: Did the server call succeed
     */
    fun removePost (postID: Int, authToken: String): Boolean {
        val param: RequestParameter = RequestParameter(token = authToken)
        val call: Call<Boolean> = server.removePost(postID, param)

        return call.execute().body() ?: false
    }


    //------------------------------------- ProjectParticipantsController -------------------------------------
    /**
     * @param userToAdd: The id of the user that should be added to the project
     * @param projectID: The id of the project to which the user is to be added
     */
    fun addUser(userID: String, projectId: Long, authToken: String): Boolean {
        //TODO: IMPLEMENT
        val param: RequestParameter = RequestParameter(token = authToken)
        //val call: Call<String> = server.addUser(userID, )

        //return call.execute().body() ?: false
        return false
    }

    /**
     * @param userToRemove: The id of the user that sould be removed from the project
     * @param projectID: The id of the project from which the user should be removed
     */
    fun removeUser(userToRemove: String, projectID: Long, authToken: String): Boolean {
        val params: RemoveUserParameter = RemoveUserParameter(authToken, userToRemove = userToRemove, projectID = projectID)

        val call: Call<Boolean> = server.removeUser(projectID, params)

        return call.execute().body() ?: false
    }

    /**
     * @return LONG: Returns the id of the created project. Returns -1 if an error occured
     */
    fun addProject(authToken: String): Long {
        val param: RequestParameter = RequestParameter(token = authToken)

        val call: Call<Long> = server.addProject(param)

        return call.execute().body() ?: -1
    }


    //------------------------------------- Delta Controller -------------------------------------
    /** Uploads a Project Command to the Server
     *
     * @return Boolean: True if uploaded successfully, otherwise false
     */
    suspend fun saveDelta(projectID: Long, projectCommand: String, authToken: String): Boolean {
        val params: SaveDeltaParameter = SaveDeltaParameter(token = authToken, projectCommand)

        val call: Call<Boolean> = server.saveDelta(projectID, params)

        return call.execute().body() ?: false
    }

    fun getDelta(projectID: Long, authToken: String): Collection<Delta> {
        val param: RequestParameter = RequestParameter(token = authToken)

        val call: Call<Collection<Delta>> = server.getDelta(projectID, param)

        return call.execute().body() ?: mutableListOf<Delta>()
    }

    fun providedOldData(projectCommand: String, forUser: String, initialAdded: LocalDateTime, initialAddedBy: String, projectID: Long, wasAdmin: Boolean, authToken: String): Boolean {
        val params: ProvideOldDataParameter = ProvideOldDataParameter(authToken, projectCommand, forUser, initialAdded, initialAddedBy, wasAdmin)

        val call: Call<Boolean> = server.provideOldData(projectID, params)

        return call.execute().body() ?: false
    }

    /**
     * @return LocalDateTime: the requested time how long comments stay on the server before they get deleted. If an error occured returns "0001-01-01T00:00" // TODO Überprüfe grammatik
     */
    fun getRemoveTime(authToken: String): LocalDateTime {
        val param: RequestParameter = RequestParameter(authToken)

        val call: Call<LocalDateTime> = server.getRemoveTime(param)

        return call.execute().body() ?: LocalDateTime.parse("0001-01-01T00:00")
    }

    //------------------------------------- FetchRequestController -------------------------------------
    fun demandOldData(projectID: Long, requestInfo: String, authToken: String): Boolean {
        val params: DemandOldDataParameter = DemandOldDataParameter(token = authToken, requestInfo)

        val call: Call<Boolean> = server.demandOldData(projectID, params)

        return call.execute().body() ?: false
    }

    fun getFetchRequests(projectID: Long, authToken: String): Collection<FetchRequest> {
        val param: RequestParameter = RequestParameter(authToken)

        val call: Call<Collection<FetchRequest>> = server.getFetchRequest(projectID, param)

        return call.execute().body() ?: emptyList()
    }
}