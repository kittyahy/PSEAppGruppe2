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
import retrofit2.http.Query
import java.time.LocalDateTime

/**
 * Declares all possible REST server calls
 */
interface ServerEndpoints
{
    // TODO: Java docs für Methoden im Interface

    // Greeting Controller
    @GET("greet")
    fun greet(): Call<String>

    // Post Controller
    @GET("Posts"+"/allPreview")
    fun getAllPostPreview(@Header("token") token: String): Call<List<PostPreview>>

    @GET("Posts"+"/detail/{post}")
    fun getPostDetail(@Header("token") token: String, @Path("post") fromPost: Int): Call<List<TemplateDetail>>

    @GET("Posts"+"/{post}/projectTemplate")
    fun getProjectTemplate(@Header("token") token: String, @Path("post") fromPost: Int): Call<String>

    @GET("Posts"+"/{post}/{template}")
    fun getGraphTemplate(@Header("token") token: String, @Path("post") fromPost: Int,
                         @Path("template") templateNumber: Int): Call<String>

    @POST("Posts"+"/add")
    fun addPost(@Header("token") token: String, @Body params: AddPostParameter): Call<Int>

    @DELETE("Posts"+"/remove/{post}")
    fun removePost(@Header("token") token: String, @Path("post") postID: Int): Call<Boolean>


    // ProjectParticipantsController
    @POST("OnlineDatabase"+"/addUser/{id}")
    fun addUser(@Header("token") token: String, @Path("id") projectId: Long): Call<String>

    @DELETE("OnlineDatabase"+"/removeUser/{id}")
    fun removeUser(@Header("token") token: String, @Path("id") projectId: Long,
                    @Query("userToRemove") userToRemove: String): Call<Boolean>

    @POST("OnlineDatabase"+"/newProject")
    fun addProject(@Header("token") token: String, @Body projectDetails: String): Call<Long>

    // TODO: Create RDSAPI Methods
    @GET("OnlineDatabase"+"/{id}/participants")
    fun getParticipants(@Header("token") token: String, @Path("id") projectId: Long): Call<List<String>>

    @GET("OnlineDatabase"+"/{id}/admin")
    fun getAdmin(@Header("token") token: String, @Path("id") projectId: Long): Call<String>

    // DeltaController
    @POST("OnlineDatabase/Delta"+"/save/{id}")
    suspend fun saveDelta(@Header("token") token: String, @Path("id") projectId: Long,
                  @Body command: String): Boolean

    @GET("OnlineDatabase/Delta"+"/get/{id}")
    fun getDelta(@Header("token") token: String, @Path("id") projectId: Long): Call<Collection<Delta>>

    @POST("OnlineDatabase/Delta"+"/provide/{id}")
    fun provideOldData(@Header("token") token: String, @Path(value = "id") projectId: Long,
                       @Body params: ProvideOldDataParameter): Call<Boolean>

    @GET("OnlineDatabase/Delta"+"/time")
    fun getRemoveTime(@Header("token") token: String): Call<Long>


    // FetchRequestController
    @POST("OnlineDatabase/request"+"/need/{id}")
    fun demandOldData(@Header("token") token: String, @Path("id") projectID: Long,
        @Body requestInfo: String): Call<Boolean>

    @GET("OnlineDatabase/request"+"/provide/{id}")
    fun getFetchRequest(@Header("token") token: String, @Path("id") projectId: Long): Call<Collection<FetchRequest>>
}