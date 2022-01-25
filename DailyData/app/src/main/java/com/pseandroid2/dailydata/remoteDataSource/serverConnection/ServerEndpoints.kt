package com.pseandroid2.dailydata.remoteDataSource.serverConnection

import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandInfo
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.AddPostParameter

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.Delta
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.FetchRequest

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.DemandOldDataParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.ProvideOldDataParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.RemoveUserParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.RequestParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.SaveDeltaParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.PostPreview
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.TemplateDetail
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
    fun getAllPostPreview(@Body param: RequestParameter): Call<List<PostPreview>>

    @GET("/Posts"+"/detail/{post}")
    fun getPostDetail(@Path("post") fromPost: Int,
                      @Body param: RequestParameter): Call<List<TemplateDetail>>

    @GET("/Posts"+"/{post}/projectTemplate")
    fun getProjectTemplate(@Path("post") fromPost: Int,
                           @Body param: RequestParameter): Call<String>

    @GET("/Posts"+"/{post}/{template}")
    fun getGraphTemplate(@Path("post") fromPost: Int, @Path("template") templateNumber: Int,
                         @Body param: RequestParameter): Call<String>

    @POST("/Posts"+"/add")
    fun addPost(@Body params: AddPostParameter): Call<Int>

    @DELETE("/Posts"+"/remove/{post}")
    fun removePost(@Path("post") postID: Int,
                   @Body param: RequestParameter): Call<Boolean>


    // ProjectParticipantsController
    @GET("/OnlineDatabase"+"/addUser/{id}")
    fun addUser(@Path("id") projectId: Long,
                @Body param: RequestParameter): Call<Boolean>

    @DELETE("/OnlineDatabase"+"/removeUser/{id}")
    fun removeUser(@Path("id") projectId: Long,
                   @Body param: RemoveUserParameter
    ): Call<Boolean>

    @POST("/OnlineDatabase"+"/newProject")
    fun addProject(@Body param: RequestParameter): Call<Long>


    // DeltaController
    @POST("/OnlineDatabase/Delta"+"/save/{projectId}")
    suspend fun saveDelta(@Path("projectId") projectId: Long,
                  @Body saveDeltaParameter: SaveDeltaParameter): Call<Boolean>

    @GET("/OnlineDatabase/Delta"+"/get/{projectId}")
    fun getDelta(@Path("projectID") projectId: Long,
                 @Body requestParameter: RequestParameter): Call<Collection<Delta>>

    @POST("/OnlineDatabase/Delta"+"/provide/{projectId}")
    fun provideOldData(@Path(value = "projectID") projectId: Long,
                       @Body params: ProvideOldDataParameter): Call<Boolean>

    @GET("/OnlineDatabase/Delta"+"/time")
    fun getRemoveTime(@Body param: RequestParameter): Call<LocalDateTime>


    // FetchRequestController
    @GET("/OnlineDatabase/request"+"/need/{id}")
    fun demandOldData(@Path("id") projectID: Long,
                      @Body param: DemandOldDataParameter): Call<Boolean>

    @GET("/OnlineDatabase/request"+"/provide/{id}")
    fun getFetchRequest(@Path("id") projectId: Long,
                        @Body param: RequestParameter): Call<Collection<FetchRequest>>
}