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
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.GraphTemplate
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Post
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.PostDetail
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ProjectTemplate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class ServerHandler(private val appDataBase: AppDataBase, private val api: RemoteDataSourceAPI) {
    //TODO("Anton changes")
    fun getPostPreviews(): List<Post> {
        return TODO() // Implementierung
    }

    fun getPostDetail(postId: Int): PostDetail {
        return TODO()
    }

    fun getProjectTemplate(postId: Int): ProjectTemplate {
        return TODO()
    }

    fun getGraphTemplate(postId: Int, index: Int): GraphTemplate {
        return TODO()
    }

    fun isServerCurrentlyReachable(): Boolean {
        return api.connectionToServerPossible()
    }

    fun login(email: String, password: String) { //Todo erweiterbarkeit
        api.signInUser(email, password, SignInTypes.EMAIL)
    }

    fun signUp(email: String, password: String) { //Todo erweiterbarkeit
        api.registerUser(email, password, SignInTypes.EMAIL)
    }

    fun downloadProjectTemplate(id : Int) {

    }

    fun downloadGraphTemplate(projectId : Int, graphId : Int) {

    }

}