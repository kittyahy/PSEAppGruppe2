package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project

class AddRow : ProjectCommand() {
    companion object {
        fun isPossible(project: Project) : Boolean {
            return true
        }
    }

}
