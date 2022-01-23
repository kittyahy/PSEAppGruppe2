package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses

class Member(
    override val id: Long,
    val name: String
): Identifiable {
    override fun deleteIsPossible(): Boolean {
        TODO("Not yet implemented")
    }

    //@throws IllegalOperationException
    override fun delete() {
        TODO("Not yet implemented. Wish kriterium")
    }
}
