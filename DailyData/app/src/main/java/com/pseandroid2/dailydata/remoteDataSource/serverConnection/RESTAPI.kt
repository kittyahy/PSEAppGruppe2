package com.pseandroid2.dailydata.remoteDataSource.serverConnection

import com.pseandroid2.dailydata.model.GraphTemplate
import com.pseandroid2.dailydata.model.ProjectTemplate
import java.time.LocalDateTime

class RESTAPI {
    val networkLocation: String = ""
    val greetingController: String = ""
    val postsController: String = ""
    val projectParticipantsController: String = ""
    val deltaController: String = ""
    val oldDataController: String = ""

    // Greetings Controller
    fun greets(): Boolean {
        // TODO: Implement Method
        return false
    }

    // Posts Controller
    fun getAllPostsPreview(authToken: String): Collection<String> {
        // TODO: Implement Method
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

    // ProjectParticipantsController
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

    // Delta Controller
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
        return LocalDateTime.parse("0000-00-00 00:00")
    }

    // FetchRequestController
    fun demandOldData(userID: String, projectID: Long, requestInfo: String, authToken: String) {
        // TODO: Implement Method
    }

    fun getFetchRequests(userID: String, projectID: Long, authToken: String): Collection<String> {
        // TODO: Implement Method
        return mutableListOf("")
    }
}