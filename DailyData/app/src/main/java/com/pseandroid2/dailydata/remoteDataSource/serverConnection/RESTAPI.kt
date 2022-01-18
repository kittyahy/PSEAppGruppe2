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

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerEndpoints
import com.pseandroid2.dailydata.model.GraphTemplate
import com.pseandroid2.dailydata.model.ProjectTemplate
import java.time.LocalDateTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

class RESTAPI {
    private var baseUrl: String = "http://myserver.com/server/" // TODO: Die URL unseres Servers verwenden

    // Greetings Controller
    private val greetingController: String = ""
    private val greets: String = ""

    // Posts Controller
    private val postsController: String = ""
    private val getAllPostsPreview: String = ""
    private val getPostDetail: String = ""
    private val getProjectTemplate: String = ""
    private val getGraphTemplate: String = ""
    private val addPost: String = ""
    private val removePost: String = ""

    // ProjectParticipantsController
    private val projectParticipantsController: String = ""
    private val addUser: String = ""
    private val removeUser: String = ""
    private val addProject: String = ""

    // Delta Controller
    private val deltaController: String = ""
    private val saveDelta: String = ""
    private val getDelta: String = ""
    private val providedOldData: String = ""
    private val getRemoveTime: String = ""

    // FetchRequestController
    private val fetchRequestController: String = ""
    private val demandOldData: String = ""
    private val getFetchRequests: String = ""

    private var retrofit: Retrofit
    private val server: ServerEndpoints

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
            //.create(ServerEndpoints)

        server = retrofit.create(ServerEndpoints::class.java)

        // Create Services // TODO mal schauen, ob ich das brauche
        // https://square.github.io/retrofit/
        // https://dev.to/paulodhiambo/kotlin-and-retrofit-network-calls-2353
        // Synchrone Calls: https://futurestud.io/tutorials/retrofit-synchronous-and-asynchronous-requests
    }
    // Note: There is no need to send the User ID of the current user to the server, as this can be read from the Firebase authToken

    //------------------------------------- Greetings Controller -------------------------------------
    /**
     * @return Boolean: Returns true, if the server could be successfully reached. Otherwise return false
     */
    fun greet(): Boolean {
        val greetingCall = server.greet()

        val greeting: String = greetingCall.execute().body() ?: ""

        if (greeting == "hello") {
            return true
        }
        return false
    }


    //------------------------------------- Posts Controller -------------------------------------
    fun getAllPostsPreview(authToken: String): Collection<String> {
        // TODO: Implement Method

        //@GET(relativeURL)
        //fun fetchPosts(@Header(authToken) token: String): Call<StringRequest>

        return mutableListOf("")
    }

    fun getPostDetail(fromPost: Int, authToken: String): Collection<String> {
        // TODO: Implement Method
        return mutableListOf("")
    }

    fun getProjectTemplate(fromPost: Int, authToken: String): String {
        // TODO: Implement Method
        return ""
    }

    fun getGraphTemplate(fromPost: Int, templateNumber: Int, authToken: String): String {
        // TODO: Implement Method
        return ""
    }

    fun addPost (postPreview: String, userID: String, projectTemplate: String, graphTemplate: Collection<String>, authToken: String) {
        // TODO: Implement Method
        return
    }

    fun removePost (postID: Int, userID: String, authToken: String) {
        // TODO: Implement Method
        return
    }


    //------------------------------------- ProjectParticipantsController -------------------------------------
    fun addUser(userID: String, projectId: Long, authToken: String): Boolean {
        // TODO: Implement Method
        return false
    }

    fun removeUser(userToRemove: String, projectID: Long, userID: String, authToken: String): Boolean {
        // TODO: Implement Method
        return false
    }

    fun addProject(userID: String, authToken: String): Long {
        // TODO: Implement Method
        return -1
    }


    //------------------------------------- Delta Controller -------------------------------------
    fun saveDelta(projectID: Long, projectCommand: String, userID: String, authToken: String) {
        // TODO: Implement Method
        return
    }

    fun getDelta(projectID: Long, userID: String, authToken: String): Collection<String> {
        // TODO: Implement Method
        return mutableListOf("")
    }

    fun providedOldData(projectCommand: String, forUser: String, initialAdded: LocalDateTime, initialAddedBy: String, projectID: Long, wasAdmin: Boolean, authToken: String) {
        // TODO: Implement Method
        return
    }

    // TODO: Wahrscheinlich ist das hier nicht LocalDateTime -> Überprüfen
    fun getRemoveTime(authToken: String): LocalDateTime {
        // TODO: Implement Method
        return LocalDateTime.parse("0001-01-01T00:00")
    }

    //------------------------------------- FetchRequestController -------------------------------------
    fun demandOldData(userID: String, projectID: Long, requestInfo: String, authToken: String) {
        // TODO: Implement Method
        return
    }

    fun getFetchRequests(userID: String, projectID: Long, authToken: String): Collection<String> {
        // TODO: Implement Method
        return mutableListOf("")
    }
}