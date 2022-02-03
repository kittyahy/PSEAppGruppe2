package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.forRepoReturns.TemplateDetailWithPicture
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import kotlinx.coroutines.flow.Flow

class Post : Identifiable {
    override var id: Int = -1
    lateinit var postEntries: List<PostEntry>

    constructor(id: Int, list: List<PostEntry>) {
        this.id = id
        this.postEntries = list
    }

    constructor(id: Int, templateDetails: Collection<TemplateDetailWithPicture>) {
        this.id = id
        val postEntries = ArrayList<PostEntry>()
        for (detail in templateDetails) {
            postEntries.add(PostEntry(detail))
        }
        this.postEntries = postEntries
    }

    fun getProjectTemplate(): ProjectTemplate {
        TODO()
    }

    fun getGraphTemplate(id: Int): GraphTemplate {
        TODO()
    }

    override lateinit var executeQueue: ExecuteQueue
    override lateinit var project: Project

    override fun deleteIsPossible(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun delete() {
        TODO("Not yet implemented")
    }
}