package com.pseandroid2.dailydata.repository.viewModelInterface

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.GraphTemplate
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.Post
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.PostDetail
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.ProjectTemplate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class PostHandler (appDataBase: AppDataBase){
    fun getPostPreviews() : Flow<List<Post>> {
        return MutableStateFlow(ArrayList<Post>()) // TODO Implementierung
    }
    fun getPostDetail(id: Long): PostDetail {
        return TODO()
    }
    fun getProjectTemplate(id: Long): ProjectTemplate {
        return TODO()
    }
    fun getGraphTemplate(id: Long, index: Int) : GraphTemplate {
        return TODO()
    }
}