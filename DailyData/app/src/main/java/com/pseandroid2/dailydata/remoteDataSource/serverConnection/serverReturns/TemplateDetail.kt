package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns

import com.pseandroid2.dailydata.model.GraphTemplate
import com.pseandroid2.dailydata.model.ProjectTemplate

data class TemplateDetail(val id: Int = 0, val detail: String = "", val isProjectTemplate: Boolean = false, val isGraphTemplate: Boolean = false)
