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

package com.pseandroid2.dailydata.model.project

import com.pseandroid2.dailydata.model.database.entities.ProjectTemplateData
import com.pseandroid2.dailydata.model.graph.GraphTemplate
import com.pseandroid2.dailydata.model.table.ArrayListLayout
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.model.users.NullUser
import com.pseandroid2.dailydata.model.users.User

class SimpleProjectTemplate(
    override val skeleton: ProjectSkeleton,
    private val layout: TableLayout,
    private val creator: User,
    override var graphs: MutableList<GraphTemplate>
) : ProjectTemplate {
    constructor(
        data: ProjectTemplateData = ProjectTemplateData(
            -1,
            "",
            "",
            -1,
            "",
            0,
            NullUser()
        )
    ) : this(
        SimpleSkeleton(
            data.id,
            data.onlineId,
            data.name,
            data.description,
            data.wallpaper,
            data.color
        ),
        ArrayListLayout(),
        data.creator,
        mutableListOf()
    )

    override fun addGraph(graph: GraphTemplate) {
        TODO("Not yet implemented")
    }

    override fun addGraphs(graphsToAdd: Collection<GraphTemplate>) {
        TODO("Not yet implemented")
    }

    override fun getTableLayout() = layout

    override fun getCreator() = creator
}