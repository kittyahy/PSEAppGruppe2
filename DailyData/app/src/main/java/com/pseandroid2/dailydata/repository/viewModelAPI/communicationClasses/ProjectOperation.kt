package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddColumn
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddGraph
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddNotification
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddRow
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddUser
import com.pseandroid2.dailydata.repository.commandCenter.commands.DeleteNotification
import com.pseandroid2.dailydata.repository.commandCenter.commands.DeleteProject
import com.pseandroid2.dailydata.repository.commandCenter.commands.DeleteRow
import com.pseandroid2.dailydata.repository.commandCenter.commands.LeaveOnlineProject
import com.pseandroid2.dailydata.repository.commandCenter.commands.PublishProject
import com.pseandroid2.dailydata.repository.commandCenter.commands.SetDescription
import com.pseandroid2.dailydata.repository.commandCenter.commands.SetTitle
import com.pseandroid2.dailydata.repository.commandCenter.commands.SetWallpaper

enum class ProjectOperation(val adminOp: Boolean) {
    SET_PROJECT_NAME(false) {
        override fun isIllegalByData(project: Project): Boolean {
            return SetTitle.isIllegal(project)
        }
    },
    SET_PROJECT_DESC(false) {
        override fun isIllegalByData(project: Project): Boolean {
            return SetDescription.isIllegal(project)
        }
    },
    DELETE(false) {
        override fun isIllegalByData(project: Project): Boolean {
            return DeleteProject.isIllegal(project)
        }
    },
    ADD_GRAPH(false) {
        override fun isIllegalByData(project: Project): Boolean {
            return AddGraph.isIllegal(project)
        }
    },
    ADD_USER(false) {
        override fun isIllegalByData(project: Project): Boolean {
            return AddUser.isIllegal(project)
        }
    },
    LEAVE_PROJECT(false) {
        override fun isIllegalByData(project: Project): Boolean {
            return LeaveOnlineProject.isIllegal(project)
        }
    },
    SET_ADMIN(false) {
        override fun isIllegalByData(project: Project): Boolean {
            return TODO("SetAdmin isIllegal")
        }
    },
    SET_COLOR(false) {
        override fun isIllegalByData(project: Project): Boolean {
            return SetWallpaper.isIllegal(project)
        }
    },
    CHANGE_NOTIFICATION(false) {
        override fun isIllegalByData(project: Project): Boolean {
            return TODO("SetNotification isIllegal")
        }
    },
    ADD_NOTIFICATION(false) {
        override fun isIllegalByData(project: Project): Boolean {
            return AddNotification.isIllegal(project)
        }
    },
    DELETE_NOTIFICATION(false) {
        override fun isIllegalByData(project: Project): Boolean {
            return DeleteNotification.isIllegal(project)
        }
    },
    PUBLISH_PROJECT(false) {
        override fun isIllegalByData(project: Project): Boolean {
            return PublishProject.isIllegal(project)
        }
    };


    open fun isIllegalByData(project: Project): Boolean = false
}