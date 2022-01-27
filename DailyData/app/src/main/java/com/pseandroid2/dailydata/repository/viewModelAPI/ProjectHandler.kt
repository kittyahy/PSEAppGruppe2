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
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ProjectTemplate
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.GraphTemplateFlow
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectFlow
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectPreviewFlow
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectTemplateFlow
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@InternalCoroutinesApi
class ProjectHandler(
    val projectPreviewFlow: ProjectPreviewFlow,
    val projectTemplateFlow: ProjectTemplateFlow,
    val graphTemplateFlow: GraphTemplateFlow,
    private val appDataBase: AppDataBase
) {
    fun getProjectByID(id: Int): Flow<Project> {
        return ProjectFlow(appDataBase, id).getProjectFlow()
    }

    fun newProject(
        name: String,
        description: String,
        wallpaper: Int,
        table: List<Column>,
        buttons: List<Button>,
        notification: List<Notification>,
        graphs: List<Graph>
    ): Project {
        return TODO()
    }
    fun joinOnlineProject(onlineID: Long): Int {
        return TODO()
    }
    fun getProjectTemplateByID () {
        TODO()
    }
}