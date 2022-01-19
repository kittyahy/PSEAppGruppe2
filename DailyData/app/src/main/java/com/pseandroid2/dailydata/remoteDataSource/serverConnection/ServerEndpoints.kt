package com.pseandroid2.dailydata.remoteDataSource.serverConnection

import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandInfo
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.AddPostParameter

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.Delta
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.FetchRequest

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.DemandOldDataParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.ProvideOldDataParameter
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
    fun getAllPostPreview(@Body param: RequestParameter): List<String>

    @GET("/Posts"+"/detail/{post}")
    fun getPostDetail(@Path("post") fromPost: Int,
                      @Body param: RequestParameter): List<String>

    @GET("/Posts"+"/{post}/projectTemplate")
    fun getProjectTemplate(@Path("post") fromPost: Int,
                           @Body param: RequestParameter): String

    @GET("/Posts"+"/{post}/{template}")
    fun getGraphTemplate(@Path("post") fromPost: Int, @Path("template") templateNumber: Int,
                         @Body param: RequestParameter)

    @POST("/Posts"+"/add")
    fun addPost(@Body params: AddPostParameter)

    @DELETE("/Posts"+"/remove/{post}")
    fun removePost(@Path("post") postID: String,
                   @Body param: RequestParameter)

    // ProjectParticipantsController
    @GET("/OnlineDatabase"+"/addUser/{id}")
    fun addUser(@Path("id") projectId: Long,
                @Body param: RequestParameter): Boolean

    @DELETE("/OnlineDatabase"+"/removeUser/{id}")
    fun removeUser(@Path("id") projectId: Long,
                   @Body param: RequestParameter): Boolean

    @POST("/OnlineDatabase"+"/newProject")
    fun addProject(@Body param: RequestParameter): Long


    // DeltaController
    @POST("/OnlineDatabase/Delta"+"/save/{projectId}")
    suspend fun saveDelta(@Path("projectId") projectId: ProjectCommandInfo,
                  @Body saveDeltaParameter: SaveDeltaParameter)

    @GET("/OnlineDatabase/Delta"+"/get/{projectId}")
    fun getDelta(@Path("projectID") projectId: Long,
                 @Body requestParameter: RequestParameter): MutableList<Delta>

    @POST("/OnlineDatabase/Delta"+"/provide/{projectId}")
    suspend fun provideOldData(@Path(value = "projectID") projectId: Long,
                       @Body params: ProvideOldDataParameter)

    @GET("/OnlineDatabase/Delta"+"/time")
    fun getRemoveTime(@Body param: RequestParameter)


    // FetchRequestController
    @GET("/OnlineDatabase/request"+"/need/{id}")
    fun demandOldData(@Path("id") projectID: Long,
                      @Body param: DemandOldDataParameter)

    @GET("/OnlineDatabase/request"+"/provide/{id}")
    fun getFetchRequest(@Path("id") projectId: Long,
                        @Body param: RequestParameter): List<FetchRequest>
}