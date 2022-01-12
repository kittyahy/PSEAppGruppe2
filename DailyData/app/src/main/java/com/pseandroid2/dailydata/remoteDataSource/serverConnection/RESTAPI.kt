package com.pseandroid2.dailydata.remoteDataSource.serverConnection

import com.pseandroid2.dailydata.model.GraphTemplate
import com.pseandroid2.dailydata.model.ProjectTemplate
import java.time.LocalDateTime
import retrofit2.Retrofit
import retrofit2.http.GET

class RESTAPI {
    private var BASE_URL = "http://myserver.com/server/" // TODO: Die URL unseres Servers verwenden

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
            .baseUrl(BASE_URL)
            .build();


        // Create Services
        // https://square.github.io/retrofit/
    }

    // In keiner Methode muss die UserID des Nutzenden, welcher den Command sendet, mitgegeben werden, da diese durch dass Firebase authToken ablesbar ist

    //------------------------------------- Greetings Controller -------------------------------------
    fun greets(): Boolean {
        // TODO: Implement Method
        var URL: String = BASE_URL+greetingController+greets

        return false
    }


    //------------------------------------- Posts Controller -------------------------------------
    fun getAllPostsPreview(authToken: String): Collection<String> {
        // TODO: Implement Method
        var URL: String = BASE_URL+postsController+getAllPostsPreview

        return mutableListOf("")
    }

    fun getPostDetail(fromPost: Int, authToken: String): Collection<String> {
        // TODO: Implement Method
        var URL: String = BASE_URL+postsController+getPostDetail

        return mutableListOf("")
    }

    fun getProjectTemplate(fromPost: Int, authToken: String): String {
        // TODO: Implement Method
        var URL: String = BASE_URL+postsController+getProjectTemplate

        return ""
    }

    fun getGraphTemplate(fromPost: Int, templateNumber: Int, authToken: String): String {
        // TODO: Implement Method
        var URL: String = BASE_URL+postsController+getGraphTemplate

        return ""
    }

    fun addPost (postPreview: String, userID: String, projectTemplate: String, graphTemplate: Collection<String>, authToken: String) {
        // TODO: Implement Method
        var URL: String = BASE_URL+postsController+addPost

        return
    }

    fun removePost (postID: Int, userID: String, authToken: String) {
        // TODO: Implement Method
        var URL: String = BASE_URL+postsController+removePost

        return
    }


    //------------------------------------- ProjectParticipantsController -------------------------------------
    fun addUser(userID: String, projectId: Long, authToken: String): Boolean {
        // TODO: Implement Method
        var URL: String = BASE_URL+projectParticipantsController+addUser

        return false
    }

    fun removeUser(userToRemove: String, projectID: Long, userID: String, authToken: String): Boolean {
        // TODO: Implement Method
        var URL: String = BASE_URL+projectParticipantsController+removeUser

        return false
    }

    fun addProject(userID: String, authToken: String): Long {
        // TODO: Implement Method
        var URL: String = BASE_URL+projectParticipantsController+addProject

        return -1
    }


    //------------------------------------- Delta Controller -------------------------------------
    fun saveDelta(projectID: Long, projectCommand: String, userID: String, authToken: String) {
        // TODO: Implement Method
        var URL: String = BASE_URL+deltaController+saveDelta

        return
    }

    fun getDelta(projectID: Long, userID: String, authToken: String): Collection<String> {
        // TODO: Implement Method
        var URL: String = BASE_URL+deltaController+getDelta

        return mutableListOf("")
    }

    fun providedOldData(projectCommand: String, forUser: String, initialAdded: LocalDateTime, initialAddedBy: String, projectID: Long, wasAdmin: Boolean, authToken: String) {
        // TODO: Implement Method
        var URL: String = BASE_URL+deltaController+providedOldData

        return
    }

    // TODO: Wahrscheinlich ist das hier nicht LocalDateTime -> Überprüfen
    fun getRemoveTime(authToken: String): LocalDateTime {
        // TODO: Implement Method
        var URL: String = BASE_URL+deltaController+getRemoveTime

        return LocalDateTime.parse("0000-00-00 00:00")
    }

    //------------------------------------- FetchRequestController -------------------------------------
    fun demandOldData(userID: String, projectID: Long, requestInfo: String, authToken: String) {
        // TODO: Implement Method
        var URL: String = BASE_URL+fetchRequestController+demandOldData
        return
    }

    fun getFetchRequests(userID: String, projectID: Long, authToken: String): Collection<String> {
        // TODO: Implement Method
        var URL: String = BASE_URL+fetchRequestController+getFetchRequests
        return mutableListOf("")
    }
}