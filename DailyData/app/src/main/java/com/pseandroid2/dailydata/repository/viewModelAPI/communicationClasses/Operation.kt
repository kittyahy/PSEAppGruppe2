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

enum class Operation(val type: OperationType, val adminOp: Boolean) {
    SET_PROJECT_NAME(OperationType.PROJECT, false) {
        override fun isIllegalByData(project: Project): Boolean {
            return SetTitle.isIllegal(project)
        }
    },
    SET_PROJECT_DESC(OperationType.PROJECT, false) {
        override fun isIllegalByData(project: Project): Boolean {
            return SetDescription.isIllegal(project)
        }
    },
    DELETE(OperationType.PROJECT, false) {
        override fun isIllegalByData(project: Project): Boolean {
            return DeleteProject.isIllegal(project)
        }
    },
    ADD_GRAPH(OperationType.PROJECT, false) {
        override fun isIllegalByData(project: Project): Boolean {
            return AddGraph.isIllegal(project)
        }
    },
    ADD_USER(OperationType.PROJECT, false) {
        override fun isIllegalByData(project: Project): Boolean {
            return AddUser.isIllegal(project)
        }
    },
    LEAVE_PROJECT(OperationType.PROJECT, false) {
        override fun isIllegalByData(project: Project): Boolean {
            return LeaveOnlineProject.isIllegal(project)
        }
    },
    SET_ADMIN(OperationType.PROJECT, false) {
        override fun isIllegalByData(project: Project): Boolean {
            return TODO("SetAdmin isIllegal")
        }
    },
    SET_COLOR(OperationType.PROJECT, false) {
        override fun isIllegalByData(project: Project): Boolean {
            return SetWallpaper.isIllegal(project)
        }
    },
    CHANGE_NOTIFICATION(OperationType.PROJECT, false) {
        override fun isIllegalByData(project: Project): Boolean {
            return TODO("SetNotification isIllegal")
        }
    },
    ADD_NOTIFICATION(OperationType.PROJECT, false) {
        override fun isIllegalByData(project: Project): Boolean {
            return AddNotification.isIllegal(project)
        }
    },
    DELETE_NOTIFICATION(OperationType.PROJECT, false) {
        override fun isIllegalByData(project: Project): Boolean {
            return DeleteNotification.isIllegal(project)
        }
    },
    PUBLISH_PROJECT(OperationType.PROJECT, false) {
        override fun isIllegalByData(project: Project): Boolean {
            return PublishProject.isIllegal(project)
        }
    },
    ADD_ROW(OperationType.TABLE, false) {
        override fun isIllegalByData(project: Project): Boolean {
            return AddRow.isIllegal(project)
        }
    },
    DELETE_ROW(OperationType.TABLE, false) {
        override fun isIllegalByData(project: Project): Boolean {
            return DeleteRow.isIllegal(project)
        }
    },
    ADD_COLUMN(OperationType.TABLE, false) {
        override fun isIllegalByData(project: Project): Boolean {
            return AddColumn.isIllegal(project)
        }
    };

    enum class OperationType {
        PROJECT,
        GRAPH,
        TABLE
    }

    open fun isIllegalByData(project: Project): Boolean = false
}