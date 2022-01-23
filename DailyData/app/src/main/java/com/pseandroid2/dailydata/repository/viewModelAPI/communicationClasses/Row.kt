package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

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
    //@throws IllegalOperationException
    fun setCell(indexColumn: Int, content: String) {
        if (indexColumn >= 0 && indexColumn < elements.size) {
            TODO()
        }
        TODO()
    }
}
