package com.pseandroid2.dailydata.repository.commandCenter.commands

class CreateProject (projectID: Int, commandByUser: String) : ProjectCommand(projectID, commandByUser = commandByUser, isProjectAdmin = true) {
    override fun execute() {

        super.execute()
    }

    override fun publish() {
    }
}