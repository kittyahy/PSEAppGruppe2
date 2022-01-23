package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

class Column (
    override val id: Long,
    val name: String,
    val unit: String,
    val dataType: DataType
): Identifiable {
    override fun deleteIsPossible(): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete() {
        TODO("Not yet implemented")
    }
}
