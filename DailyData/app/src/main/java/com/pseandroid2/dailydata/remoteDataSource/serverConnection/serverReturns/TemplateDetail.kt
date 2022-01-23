package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns

import com.pseandroid2.dailydata.model.GraphTemplate
import com.pseandroid2.dailydata.model.ProjectTemplate

/**
 * @param id: The id of the template
 * @param detail: The template as a JSON
 * @param isProjectTemplate: If true, than a projectTemplate is described. If false, than a graphTemplate is described
 */
data class TemplateDetail(val id: Int = 0,
                          val detail: String = "",
                          val isProjectTemplate: Boolean = false)
