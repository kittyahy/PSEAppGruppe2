package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.flows


import com.pseandroid2.dailydata.model.database.entities.ProjectData
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.ProjectPreview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class ProjectPreviewFlow (val flow: Flow<List<ProjectData>>){
    private val sharedFlow = MutableSharedFlow<List<ProjectPreview>>()
    init {
        GlobalScope.launch {
            adapt()
        }
    }

    fun getProjectPreviewFlow(): Flow<List<ProjectPreview>> {
        return sharedFlow
    }
    @InternalCoroutinesApi
    suspend fun adapt() {
        flow.collect { list ->
            val listProjectPreview = ArrayList<ProjectPreview>()
            for (data:ProjectData in list) {
                val projectPreview = ProjectPreview(data.id.toLong(), data.name, data.wallpaper)
                listProjectPreview.add(projectPreview)
            }
            sharedFlow.emit(listProjectPreview)
        }
    }
}