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

package com.pseandroid2.dailydata.repository.viewModelAPI

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.remoteDataSource.userManager.SignInTypes
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Post
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.PostPreview
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ProjectTemplate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking

class ServerHandler(private val appDataBase: AppDataBase, private val api: RemoteDataSourceAPI) {
    suspend fun getPostPreviews(): Collection<PostPreview> = coroutineScope {
        val arrayList = ArrayList<PostPreview>()
        val postPreviews = async(Dispatchers.IO) { api.getPostPreviews() }
        for (serverPreview in postPreviews.await()) {
            arrayList.add(PostPreview(serverPreview, api))
        }
        return@coroutineScope arrayList
    }

    suspend fun getPost(postId: Int): Post {
        return Post(postId, api.getPostDetail(postId))
    }


    fun getProjectTemplateById(id: Int): ProjectTemplate {
        TODO("getProjectTemplateById")
    }

    fun amILoggedIn() = flow {
        val string = api.getUserName()
        emit(string != "")
        kotlinx.coroutines.delay(500)
    }

    /**
     * If false, it would be imprudent to use the corresponding "manipulation" fun.
     * Thus it should be used to block input options from being used if false.
     * e.g. If manipulationIsPossible.first() is false,
     *      users should not be able to call manipulation().
     */
    fun loginIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        /*val flow = MutableSharedFlow<Boolean>()
        runBlocking {
            flow.emit(true)
        }*/
        //TODO Arne/Anton should this really only ever return true? Also, SharedFlows are Hot Flows,
        //i.e. they broadcast emissions to whoever is currently listening, but will not send their
        //latest emission to new subscribers
        return flow { emit(true) }
    }

    suspend fun login(email: String, password: String) { //Todo erweiterbarkeit
        api.signInUser(email, password, SignInTypes.EMAIL)
    }

    /**
     * If false, it would be imprudent to use the corresponding "manipulation" fun.
     * Thus it should be used to block input options from being used if false.
     * e.g. If manipulationIsPossible.first() is false,
     *      users should not be able to call manipulation().
     */
    fun signUpIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>()
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    suspend fun signUp(email: String, password: String) { //Todo erweiterbarkeit
        api.registerUser(email, password, SignInTypes.EMAIL)
    }

    /**
     * If false, it would be imprudent to use the corresponding "manipulation" fun.
     * Thus it should be used to block input options from being used if false.
     * e.g. If manipulationIsPossible.first() is false,
     *      users should not be able to call manipulation().
     */
    fun downloadProjectTemplateIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>()
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    fun downloadProjectTemplate(id: Int) {
        return TODO("downloadProjectTemplate")
    }

    /**
     * If false, it would be imprudent to use the corresponding "manipulation" fun.
     * Thus it should be used to block input options from being used if false.
     * e.g. If manipulationIsPossible.first() is false,
     *      users should not be able to call manipulation().
     */
    fun downloadGraphTemplateIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>()
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    fun downloadGraphTemplate(projectId: Int, graphId: Int) {
        return TODO("downloadGraphTemplate")
    }

}