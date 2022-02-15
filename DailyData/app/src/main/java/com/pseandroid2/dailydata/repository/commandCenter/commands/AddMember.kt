package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Member
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project

class AddMember(projectID: Int, private val member: Member) :
    ProjectCommand(projectID = projectID) {
    companion object {
        fun isPossible(project: Project): Boolean {
            return project.members.size < 24 && project.isOnlineProject
        }

        const val publishable: Boolean = true
    }

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        repositoryViewModelAPI.appDataBase.projectDataDAO()
            .addUser(projectID!!, member.toDBEquivalent())
        super.execute(repositoryViewModelAPI, publishQueue)
    }
}
