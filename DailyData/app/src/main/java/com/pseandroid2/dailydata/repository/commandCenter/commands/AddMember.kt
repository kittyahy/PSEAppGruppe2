package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Member
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project

class AddMember(private val id: Int, private val member: Member) : ProjectCommand() {
    companion object {
        fun isPossible(project: Project): Boolean {
            return project.members.size < 24 && project.isOnlineProject
        }
    }

    override val publishable: Boolean = true
    override suspend fun execute(
        appDataBase: AppDataBase,
        remoteDataSourceAPI: RemoteDataSourceAPI,
        publishQueue: PublishQueue
    ) {
        appDataBase.projectDataDAO().addUser(id, member.toDBEquivalent())
        super.execute(appDataBase, remoteDataSourceAPI, publishQueue)
    }
}
