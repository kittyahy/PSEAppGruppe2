package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

class Row(
    override val id: Long,
    val elements: List<String>
): Identifiable {
    override fun deleteIsPossible(): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete() {
        TODO("Not yet implemented")
    }
}
