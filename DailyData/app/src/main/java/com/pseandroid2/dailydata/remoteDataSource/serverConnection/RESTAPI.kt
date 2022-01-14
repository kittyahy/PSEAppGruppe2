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

    var retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


        // Create Services // TODO mal schauen, ob ich das brauche
        // https://square.github.io/retrofit/
        // https://dev.to/paulodhiambo/kotlin-and-retrofit-network-calls-2353
    }

    // In keiner Methode muss die UserID des Nutzenden, welcher den Command sendet, mitgegeben werden, da diese durch dass Firebase authToken ablesbar ist

    //------------------------------------- Greetings Controller -------------------------------------
    fun greets(): Boolean {
        // TODO: Implement Method
        var relativeURL: String = greetingController+greets



        return false
    }


    //------------------------------------- Posts Controller -------------------------------------
    fun getAllPostsPreview(authToken: String): Collection<String> {
        // TODO: Implement Method
        var relativeURL: String = postsController+getAllPostsPreview

        // TODO:
        //@GET(relativeURL)
        //fun fetchPosts(@Header(authToken) token: String): Call<StringRequest>

        return mutableListOf("")
    }

    fun getPostDetail(fromPost: Int, authToken: String): Collection<String> {
        // TODO: Implement Method
        var relativeURL: String = postsController+getPostDetail

        return mutableListOf("")
    }

    fun getProjectTemplate(fromPost: Int, authToken: String): String {
        // TODO: Implement Method
        var relativeURL: String = postsController+getProjectTemplate

        return ""
    }

    fun getGraphTemplate(fromPost: Int, templateNumber: Int, authToken: String): String {
        // TODO: Implement Method
        var relativeURL: String = postsController+getGraphTemplate

        return ""
    }

    fun addPost (postPreview: String, userID: String, projectTemplate: String, graphTemplate: Collection<String>, authToken: String) {
        // TODO: Implement Method
        var relativeURL: String = postsController+addPost

        return
    }

    fun removePost (postID: Int, userID: String, authToken: String) {
        // TODO: Implement Method
        var relativeURL: String = postsController+removePost

        return
    }


    //------------------------------------- ProjectParticipantsController -------------------------------------
    fun addUser(userID: String, projectId: Long, authToken: String): Boolean {
        // TODO: Implement Method
        var relativeURL: String = projectParticipantsController+addUser

        return false
    }

    fun removeUser(userToRemove: String, projectID: Long, userID: String, authToken: String): Boolean {
        // TODO: Implement Method
        var relativeURL: String = projectParticipantsController+removeUser

        return false
    }

    fun addProject(userID: String, authToken: String): Long {
        // TODO: Implement Method
        var relativeURL: String = projectParticipantsController+addProject

        return -1
    }


    //------------------------------------- Delta Controller -------------------------------------
    fun saveDelta(projectID: Long, projectCommand: String, userID: String, authToken: String) {
        // TODO: Implement Method
        var relativeURL: String = deltaController+saveDelta

        return
    }

    fun getDelta(projectID: Long, userID: String, authToken: String): Collection<String> {
        // TODO: Implement Method
        var relativeURL: String = deltaController+getDelta

        return mutableListOf("")
    }

    fun providedOldData(projectCommand: String, forUser: String, initialAdded: LocalDateTime, initialAddedBy: String, projectID: Long, wasAdmin: Boolean, authToken: String) {
        // TODO: Implement Method
        var relativeURL: String = deltaController+providedOldData

        return
    }

    // TODO: Wahrscheinlich ist das hier nicht LocalDateTime -> Überprüfen
    fun getRemoveTime(authToken: String): LocalDateTime {
        // TODO: Implement Method
        var relativeURL: String = deltaController+getRemoveTime

        return LocalDateTime.parse("0000-00-00 00:00")
    }

    //------------------------------------- FetchRequestController -------------------------------------
    fun demandOldData(userID: String, projectID: Long, requestInfo: String, authToken: String) {
        // TODO: Implement Method
        var relativeURL: String = fetchRequestController+demandOldData
        return
    }

    fun getFetchRequests(userID: String, projectID: Long, authToken: String): Collection<String> {
        // TODO: Implement Method
        var relativeURL: String = fetchRequestController+getFetchRequests
        return mutableListOf("")
    }
}