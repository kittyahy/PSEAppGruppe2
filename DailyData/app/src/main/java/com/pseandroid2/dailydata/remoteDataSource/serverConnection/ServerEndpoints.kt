package com.pseandroid2.dailydata.remoteDataSource.serverConnection

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.AddPostParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.DemandOldDataParameter

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.Delta
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.FetchRequest

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.ProvideOldDataParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.RemoveUserParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.SaveDeltaParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.PostPreview
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.TemplateDetail
import retrofit2.http.Header
import java.time.LocalDateTime

interface ServerEndpoints
{
    //TODO: Alle URLs sind noch Platzhalter, bis wir endgültige vom Server bekommen
    //TODO: Prüfe ob Rückgabe typen nullable sein können

    // Greeting Controller
    @GET("greet")
    fun greet(): Call<String>


    // Post Controller
    @GET("/Posts"+"/allPreview")
    fun getAllPostPreview(@Header("token") token: String): Call<List<PostPreview>>

    @GET("/Posts"+"/detail/{post}")
    fun getPostDetail(@Header("token") token: String, @Path("post") fromPost: Int): Call<List<TemplateDetail>>

    @GET("/Posts"+"/{post}/projectTemplate")
    fun getProjectTemplate(@Header("token") token: String, @Path("post") fromPost: Int): Call<String>

    @GET("/Posts"+"/{post}/{template}")
    fun getGraphTemplate(@Header("token") token: String, @Path("post") fromPost: Int,
                         @Path("template") templateNumber: Int): Call<String>

    @POST("/Posts"+"/add")
    fun addPost(@Header("token") token: String, @Body params: AddPostParameter): Call<Int>

    @DELETE("/Posts"+"/remove/{post}")
    fun removePost(@Header("token") token: String, @Path("post") postID: Int): Call<Boolean>


    // ProjectParticipantsController
    @GET("/OnlineDatabase"+"/addUser/{id}")
    fun addUser(@Header("token") token: String, @Path("id") projectId: Long): Call<Boolean>

    @DELETE("/OnlineDatabase"+"/removeUser/{id}")
    fun removeUser(@Header("token") token: String, @Path("id") projectId: Long,
        params: RemoveUserParameter): Call<Boolean>

    @POST("/OnlineDatabase"+"/newProject")
    fun addProject(@Header("token") token: String): Call<Long>


    // DeltaController
    @POST("/OnlineDatabase/Delta"+"/save/{projectId}")
    suspend fun saveDelta(@Header("token") token: String, @Path("projectId") projectId: Long,
                  @Body saveDeltaParameter: SaveDeltaParameter): Call<Boolean>

    @GET("/OnlineDatabase/Delta"+"/get/{projectId}")
    fun getDelta(@Header("token") token: String, @Path("projectID") projectId: Long): Call<Collection<Delta>>

    @POST("/OnlineDatabase/Delta"+"/provide/{projectId}")
    fun provideOldData(@Header("token") token: String, @Path(value = "projectID") projectId: Long,
                       @Body params: ProvideOldDataParameter): Call<Boolean>

    @GET("/OnlineDatabase/Delta"+"/time")
    fun getRemoveTime(@Header("token") token: String): Call<LocalDateTime>


    // FetchRequestController
    @POST("/OnlineDatabase/request"+"/need/{id}")
    fun demandOldData(@Header("token") token: String, @Path("id") projectID: Long,
        params: DemandOldDataParameter): Call<Boolean>

    @GET("/OnlineDatabase/request"+"/provide/{id}")
    fun getFetchRequest(@Header("token") token: String, @Path("id") projectId: Long): Call<Collection<FetchRequest>>
}