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

package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.ProjectTemplatePreview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ProjectTemplatePreviewFlow(
    private val provider: ProjectTemplateDataFlowProvider,
    private val eq: ExecuteQueue
) {
    fun getTemplatePreviews(): Flow<List<ProjectTemplatePreview>> =
        provider.provideFlow.distinctUntilChanged()
            .map { templateDataPairs ->
                val previews = mutableListOf<ProjectTemplatePreview>()
                for (pair in templateDataPairs) {
                    val preview = ProjectTemplatePreview(pair.first, pair.second)
                    @Suppress("Deprecation")
                    preview.executeQueue = eq
                    previews.add(preview)
                }
                previews.toList()
            }
}