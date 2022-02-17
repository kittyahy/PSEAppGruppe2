package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.repository.commandCenter.commands.PublishProject

enum class Operation(val type: OperationType) {
    SET_PROJECT_NAME(OperationType.PROJECT),
    SET_PROJECT_DESC(OperationType.PROJECT),
    DELETE(OperationType.PROJECT),
    ADD_GRAPH(OperationType.PROJECT),
    ADD_USER(OperationType.PROJECT),
    LEAVE_PROJECT(OperationType.PROJECT) {
        override fun isIllegalDefault(project: Project): Boolean {
            return !(project.isOnline)
        }
    },
    SET_ADMIN(OperationType.PROJECT),
    SET_COLOR(OperationType.PROJECT),
    CHANGE_NOTIFICATION(OperationType.PROJECT),
    ADD_NOTIFICATION(OperationType.PROJECT),
    DELETE_NOTIFICATION(OperationType.PROJECT),
    PUBLISH_PROJECT(OperationType.PROJECT) {
        override fun isIllegalDefault(project: Project): Boolean {
            return PublishProject.isIllegal(project)
        }
    },
    ADD_ROW(OperationType.TABLE),
    DELETE_ROW(OperationType.TABLE) {
        override fun isIllegalDefault(project: Project): Boolean {
            return project.table.getSize() <= 0
        }
    },
    ADD_COLUMN(OperationType.TABLE);

    open fun isIllegalDefault(project: Project): Boolean {
        return false
    }

    enum class OperationType {
        PROJECT,
        GRAPH,
        TABLE
    }
}