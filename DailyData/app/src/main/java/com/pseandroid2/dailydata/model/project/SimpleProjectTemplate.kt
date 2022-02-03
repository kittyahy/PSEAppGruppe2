package com.pseandroid2.dailydata.model.project

import com.pseandroid2.dailydata.model.database.entities.ProjectTemplateData
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.model.users.User

class SimpleProjectTemplate(
    private val skeleton: ProjectSkeleton,
    private val layout: TableLayout,
    private val creator: User
) : ProjectTemplate {
    constructor(data: ProjectTemplateData) : this(
        SimpleSkeleton(
            data.id,
            data.onlineId,
            data.name,
            data.description,
            data.wallpaper,
            data.color
        ),
        data.layout,
        data.creator
    )

    @Suppress("Deprecation")
    @Deprecated("Properties of Project should be accessed directly, access via Skeleton is deprecated")
    override fun getProjectSkeleton() = skeleton

    override fun getTableLayout() = layout

    override fun getCreator() = creator
}