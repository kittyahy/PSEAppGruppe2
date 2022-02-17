package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import android.graphics.Bitmap
import com.pseandroid2.dailydata.model.database.entities.ProjectTemplateData
import com.pseandroid2.dailydata.model.table.ArrayListLayout
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import kotlinx.coroutines.flow.Flow

class ProjectTemplatePreview(
    override var id: Int,
    val name: String,
    val desc: String,
    val color: Int,
    val layout: List<Column>,
    val image: Bitmap?
) : Identifiable {

    constructor(data: ProjectTemplateData, bitmap: Bitmap?) : this(
        data.id,
        data.name,
        data.description,
        data.color,
        ArrayListLayout().toColumnList(),
        bitmap
    )

    override lateinit var executeQueue: ExecuteQueue
    override lateinit var viewModelProject: ViewModelProject

    override fun deleteIsPossible(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun delete() {
        TODO("Not yet implemented")
    }
}