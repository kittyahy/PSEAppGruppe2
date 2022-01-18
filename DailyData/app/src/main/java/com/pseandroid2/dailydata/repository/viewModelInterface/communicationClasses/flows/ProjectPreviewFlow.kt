package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.flows

import com.pseandroid2.dailydata.model.database.daos.ProjectDataDAO
import com.pseandroid2.dailydata.model.database.entities.ProjectData
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.ProjectPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class ProjectPreviewFlow {
    @Inject
    private lateinit var projectDataDAO: ProjectDataDAO
    private var listProjectPreview: List<ProjectPreview> = ArrayList<ProjectPreview>()
    private val sharedFlow = MutableSharedFlow<List<ProjectPreview>>()

    fun getProjectPreviewFlow(): SharedFlow<List<ProjectPreview>> {
        return sharedFlow
    }
    @InternalCoroutinesApi
    suspend fun adapt() {
        projectDataDAO.getAllProjectData().collect { list ->
            listProjectPreview = ArrayList<ProjectPreview>()
            for (data:ProjectData in list) {
                val projectPreview = ProjectPreview(data.id.toLong(), data.name, data.wallpaper)
                (listProjectPreview as ArrayList<ProjectPreview>).add(projectPreview)
            }
            sharedFlow.emit(listProjectPreview)
        }
    }
}