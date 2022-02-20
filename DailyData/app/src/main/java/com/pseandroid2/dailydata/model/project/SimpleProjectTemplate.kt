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