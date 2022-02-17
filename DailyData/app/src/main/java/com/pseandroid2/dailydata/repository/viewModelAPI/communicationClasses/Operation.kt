package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.repository.commandCenter.commands.SetTitle

enum class Operation(val type: OperationType, val adminOp: Boolean) {
    SET_PROJECT_NAME(OperationType.PROJECT, false) {//TODO replace this with actual calculations
        override fun isIllegal(project: Project): Boolean {
            return !SetTitle.isPossible(project)
        }
    },
    SET_PROJECT_DESC(OperationType.PROJECT, false),
    DELETE(OperationType.PROJECT, false),
    ADD_GRAPH(OperationType.PROJECT, false),
    ADD_USER(OperationType.PROJECT, false),
    LEAVE_PROJECT(OperationType.PROJECT, false),
    SET_ADMIN(OperationType.PROJECT, false),
    SET_COLOR(OperationType.PROJECT, false),
    CHANGE_NOTIFICATION(OperationType.PROJECT, false),
    ADD_NOTIFICATION(OperationType.PROJECT, false),
    DELETE_NOTIFICATION(OperationType.PROJECT, false),
    PUBLISH_PROJECT(OperationType.PROJECT, false),
    ADD_ROW(OperationType.TABLE, false),
    DELETE_ROW(OperationType.TABLE, false),
    ADD_COLUMN(OperationType.TABLE, false);

    enum class OperationType {
        PROJECT,
        GRAPH,
        TABLE
    }

    open fun isIllegal(project: Project): Boolean = false
}