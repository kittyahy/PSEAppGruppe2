package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

class Button(
    override val id: Long,
    val name: String,
    val columnId: Long,
    val value: Int
): Identifiable {
    override fun deleteIsPossible(): Boolean {
        TODO("Not yet implemented")
    }

    //@throws IllegalOperationException
    override fun delete() {
        TODO("Not yet implemented")
    }
}
