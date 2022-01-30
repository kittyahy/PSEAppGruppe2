package com.pseandroid2.dailydata.model.project

import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.model.users.User

class SimpleProjectTemplate(
    private val skeleton: ProjectSkeleton,
    private val layout: TableLayout,
    private val creator: User
) : ProjectTemplate {
    override fun getProjectSkeleton() = skeleton

    override fun getTableLayout() = layout

    override fun getCreator() = creator
}