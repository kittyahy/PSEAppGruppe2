package com.pseandroid2.dailydata.remoteDataSource.serverConnection

import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandInfo

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.DemandOldDataParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.ProvideOldDataParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.RemoveUserParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.RequestParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.SaveDeltaParameter

interface ServerEndpoints
{
    //TODO: Alle URLs sind noch Platzhalter, bis wir endgültige vom Server bekommen
    //TODO: Prüfe ob Rückgabe typen nullable sein können
    // Greeting Controller
    @GET("greet")
    fun greet(): Call<String>


    // Post Controller
    @GET("/Posts"+"/allPreview")
    fun getAllPostPreview(@Header String token): List<String>

    @GET("/Posts"+"/detail/{post}")
    fun getPostDetail(@Path("post") fromPost: Int,
                      @RequestHeader token: String): List<String>

    @GET("/Posts"+"/{post}/projectTemplate")
    fun getProjectTemplate(@Path("post") fromPost: Int,
                           @RequestHeader token: String): String

    @GET("/Posts"+"/{post}/{template}")
    fun getGraphTemplate(@Path("post") fromPost: Int, @Path("template") templateNumber: Int,
                         @RequestHeader token: String)

    @POST("/Posts"+"/add")
    fun addPost(@RequestHeader token: String, @RequestParam postPreview: String, @RequestParam projectTemplate: String, @RequestParam graphTemplates: List<String>)

    @DELETE("/Posts"+"/remove/{post}")
    fun removePost(@Path("post") postID: String,
                   @RequestHeader token: String,)


    // ProjectParticipantsController
    @GET("/OnlineDatabase"+"/addUser/{id}")
    fun addUser(@Path("id") projectId: Long, @RequestHeader token: String, @RequestAttribute user: String): Boolean

    @DELETE("/OnlineDatabase"+"/removeUser/{id}")
    fun removeUser(@Path("id") projectId: Long,
                   @RequestHeader token: String, @RequestParam userToRemove: String): Boolean

    @POST("/OnlineDatabase"+"/newProject")
    fun addProject(@RequestHeader token: String, @RequestAttribute user: String): Long


    // DeltaController
    @POST("/OnlineDatabase/Delta"+"/save/{projectId}")
    fun saveDelta(@Path("projectId") projectId: ProjectCommandInfo,
                  @Body saveDeltaParameter: SaveDeltaParameter)

    @GET("/OnlineDatabase/Delta"+"/get/{projectId}")
    fun getDelta(@Path("projectID") projectId: Long, @RequestHeader token: String): MutableList<Delta>

    @POST("/OnlineDatabase/Delta"+"/provide/{projectId}")
    fun provideOldData(@Path(value = "projectID") projectId: Long,
                       @RequestParam command: String,
                       @RequestParam forUser: String, @RequestParam initialAdded: LocalDateTime,
                       @RequestParam initialAddedBy: String, @RequestParam wasAdmin: Boolean,
                       @RequestAttribute user: String, @RequestHeader token: String)

    @GET("/OnlineDatabase/Delta"+"/time")
    fun getRemoveTime(@RequestHeader token: String)


    // FetchRequestController
    @GET("/OnlineDatabase/request"+"/need/{id}")
    fun demandOldData(@Path("id") projectID: Long,
                      @RequestAttribute user: String, @RequestHeader token: String?, @RequestParam requestInfo: String?)

    @GET("/OnlineDatabase/request"+"/provide/{id}")
    fun getFetchRequest(@Path("id") projectId: Long,
                        @RequestAttribute user: String, @RequestHeader token: String): List<FetchRequest>
}