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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.time.LocalDateTime

/**
 * Carries out all calls to our server
 * Die baseURL vom Testserver: "http://00495284-8fb3-4727-9bf9-10353bd82b99.ka.bw-cloud-instance.org:8080"
 * Die baseURL vom echtem Server: "http://261ee33a-ba27-4828-b5df-f5f8718defe8.ka.bw-cloud-instance.org:8080"  NICHT ANFASSEN - Verbrennungsgefahr!
 */
class RESTAPI {

    /* Das ist der echte Server: Finger Weg
     private var baseUrl: String =
       "http://261ee33a-ba27-4828-b5df-f5f8718defe8.ka.bw-cloud-instance.org:8080" // The URL from our server   */

    // Das ist ein TestServer, have fun !! sehen sehr gleich aus die Links.
    private var baseUrl: String =
        "http://00495284-8fb3-4727-9bf9-10353bd82b99.ka.bw-cloud-instance.org:8080"

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
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun greet(): Boolean {
        try {
            val call = server.greet()

            if (call.isSuccessful && call.body() != null) {
                if (call.body() == "Hello") {
                    return true
                }
            }
        } catch (e: RuntimeException) {
            throw ServerNotReachableException()
        }
        return false
    }

    //------------------------------------- Posts Controller -------------------------------------
    /**
     * Gets post previews from the server
     *
     * @param authToken: The authentication token
     * @return Collection<PostPreview>: The previews of the posts
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getAllPostsPreview(authToken: String): Collection<PostPreview> {
        try {
            val call = server.getAllPostPreview(authToken)

            if (call.isSuccessful && call.body() != null) {
                return call.body()!!
            }
        } catch (e: RuntimeException) {
            throw ServerNotReachableException()
        }
        return emptyList()
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
    suspend fun getPostDetail(fromPost: Int, authToken: String): Collection<TemplateDetail> {
        try {
            val call = server.getPostDetail(authToken, fromPost)

            if (call.isSuccessful && call.body() != null) {
                return call.body()!!
            }
        } catch (e: RuntimeException) {
            throw ServerNotReachableException()
        }
        return emptyList()
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
        try {
            val call = server.getProjectTemplate(authToken, fromPost)
            if (call.isSuccessful && call.body() != null) {
                return call.body()!!
            }
        } catch (e: RuntimeException) {
            throw ServerNotReachableException()
        }
        return ""
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
        try {
            val call = server.getGraphTemplate(authToken, fromPost, templateNumber)
            if (call.isSuccessful && call.body() != null) {
                return call.body()!!
            }
        } catch (e: RuntimeException) {
            throw ServerNotReachableException()
        }
        return ""
    }

    // Wish-criteria
    /**
     * Uploads a post to the server
     *
     * @param postPreview:      The preview of the post that should be added
     * @param projectTemplate:  The project template. The first element of the Pair is the template as a JSON, the second one is the detailView of the template
     * @param graphTemplates:   The graph templates. The first element of a Pair is the template as a JSON, the second one is the detailView of the template
     * @param authToken:        The authentication token
     * @return Int: The PostID of the new post. -1 if the call didn't succeed, 0 if the user reached his limit of uploaded posts
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun addPost(
        postPreview: PostPreviewWrapper,
        projectTemplate: Pair<String, TemplateDetailWrapper>,
        graphTemplates: List<Pair<String, TemplateDetailWrapper>>,
        authToken: String
    ): Int {
        val params = AddPostParameter(postPreview, projectTemplate, graphTemplates)

        try {
            val call = server.addPost(authToken, params)
            if (call.isSuccessful && call.body() != null) {
                return call.body()!!
            }
        } catch (e: RuntimeException) {
            throw ServerNotReachableException()
        }
        return -1
    }

    // Wish-criteria
    /**
     * Deletes a post from the server
     *
     * @param postID:       The id of the post that should be removed from the server
     * @param authToken:    The authentication token
     * @return Boolean:     Did the server call succeed
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun removePost(postID: Int, authToken: String): Boolean {
        try {
            val call = server.removePost(authToken, postID)
            if (call.isSuccessful && call.body() != null) {
                return call.body()!!
            }
        } catch (e: RuntimeException) {
            throw ServerNotReachableException()
        }
        return false
    }


    //------------------------------------- ProjectParticipantsController -------------------------------------
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
        try {
            val call = server.addUser(authToken, projectID)
            if (call.isSuccessful && call.body() != null) {
                return call.body()!!
            }
        } catch (e: RuntimeException) {
            throw ServerNotReachableException()
        }
        return ""
    }

    /**
     * Removes a user from a project
     *
     * @param userToRemove: The id of the user that should be removed from the project
     * @param projectID:    The id of the project from which the user should be removed
     * @param authToken:    The authentication token
     * @return Boolean: Did the server call succeed
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun removeUser(userToRemove: String, projectID: Long, authToken: String): Boolean {
        try {
            val call = server.removeUser(authToken, projectID, userToRemove = userToRemove)
            if (call.isSuccessful && call.body() != null) {
                return call.body()!!
            }
        } catch (e: RuntimeException) {
            throw ServerNotReachableException()
        }
        return false
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
        try {
            val call = server.addProject(authToken, projectDetails)
            if (call.isSuccessful && call.body() != null) {
                return call.body()!!
            }
        } catch (e: RuntimeException) {
            throw ServerNotReachableException()
        }
        return -1
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
        try {
            val call = server.getParticipants(authToken, projectID)
            if (call.isSuccessful && call.body() != null) {
                return call.body()!!
            }
        } catch (e: RuntimeException) {
            throw ServerNotReachableException()
        }
        return listOf()
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
        try {
            val call = server.getAdmin(authToken, projectID)
            if (call.isSuccessful && call.body() != null) {
                return call.body()!!
            }
        } catch (e: RuntimeException) {
            throw ServerNotReachableException()
        }
        return ""
    }

    //------------------------------------- Delta Controller -------------------------------------
    /**
     * Uploads a Project Command to the Server
     *
     * @param projectID:        The id of the project to which the project command should be uploaded
     * @param projectCommand:   The project command that should be send to the server (as JSON)
     * @param authToken:        The authentication token
     * @return Boolean: True if uploaded successfully, otherwise false
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun saveDelta(projectID: Long, projectCommand: String, authToken: String): Boolean {
        try {
            val call = server.saveDelta(authToken, projectID, projectCommand)
            if (call.isSuccessful && call.body() != null) {
                return call.body()!!
            }
        } catch (e: RuntimeException) {
            throw ServerNotReachableException()
        }
        return false
    }

    /**
     * Gets the project commands of a project
     *
     * @param projectID: The id of the project whose delta (projectCommands) you want to load into the FetchRequestQueue
     * @param authToken: The authentication token
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getDelta(projectID: Long, authToken: String): Collection<Delta> {
        try {
            val call = server.getDelta(token = authToken, projectID)
            if (call.isSuccessful && call.body() != null) {
                return call.body()!!
            }
        } catch (e: RuntimeException) {
            throw ServerNotReachableException()
        }
        return listOf()
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
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
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
            ProvideOldDataParameter(
                command = projectCommand, forUser = forUser, initialAdded = initialAdded.toString(),
                initialAddedBy = initialAddedBy, wasAdmin = wasAdmin
            )

        try {
            val call = server.provideOldData(authToken, projectID, params)
            if (call.isSuccessful && call.body() != null) {
                return call.body()!!
            }
        } catch (e: RuntimeException) {
            throw ServerNotReachableException()
        }
        return false
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
        try {
            val call = server.getRemoveTime(authToken)
            if (call.isSuccessful && call.body() != null) {
                return call.body()!!
            }
        } catch (e: RuntimeException) {
            throw ServerNotReachableException()
        }
        return -1
    }

    //------------------------------------- FetchRequestController -------------------------------------
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
        try {
            val call = server.demandOldData(token = authToken, projectID, requestInfo = requestInfo)
            if (call.isSuccessful && call.body() != null) {
                return call.body()!!
            }
        } catch (e: RuntimeException) {
            throw ServerNotReachableException()
        }
        return false
    }

    /**
     * Gets fetch requests from the server
     *
     * @param projectID: The id of the project from which the fetch requests should be downloaded
     * @param authToken: The authentication token
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getFetchRequests(projectID: Long, authToken: String): Collection<FetchRequest> {
        try {
            val call = server.getFetchRequest(authToken, projectID)
            if (call.isSuccessful && call.body() != null) {
                return call.body()!!
            }
        } catch (e: RuntimeException) {
            throw ServerNotReachableException()
        }
        return emptyList()
    }

    /**
     * Gets all the postIDs from the posts which one user uploaded
     * Note: This method is just for testing
     *
     * @param authToken:    The authentication token
     * @return List<Int>:   The postIDs from the posts
     * @throws java.io.IOException: Input for server or server output was wrong
     * @throws ServerNotReachableException: Timeout when trying to communicate with server
     */
    suspend fun getPostsFromUser(authToken: String): List<Int> {
        try {
            val call = server.getPostsFromUser(authToken)
            if (call.isSuccessful && call.body() != null) {
                return call.body()!!
            }
        } catch (e: RuntimeException) {
            throw ServerNotReachableException()
        }
        return emptyList()
    }
}