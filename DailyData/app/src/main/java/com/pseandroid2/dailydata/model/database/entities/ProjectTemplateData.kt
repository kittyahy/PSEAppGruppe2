package com.pseandroid2.dailydata.model.database.entities

import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.model.users.User

/**
 * This class contains all components of a project Template.
 * This class can be used out side the model, because it does not work directly on a database.
 */
data class ProjectTemplateData(
    val id: Int,
    val name: String,
    val description: String,
    val onlineId: Long,
    val wallpaper: String,
    val color: Int,
    val creator: User,
    val layout: TableLayout
)