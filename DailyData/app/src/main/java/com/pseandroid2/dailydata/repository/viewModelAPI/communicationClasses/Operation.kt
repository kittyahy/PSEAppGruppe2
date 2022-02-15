package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

enum class Operation(val type: OperationType) {
    SET_PROJECT_NAME(OperationType.PROJECT),
    SET_PROJECT_DESC(OperationType.PROJECT),
    DELETE(OperationType.PROJECT),
    ADD_GRAPH(OperationType.PROJECT),
    ADD_USER(OperationType.PROJECT),
    LEAVE_PROJECT(OperationType.PROJECT),
    SET_ADMIN(OperationType.PROJECT),
    SET_COLOR(OperationType.PROJECT),
    CHANGE_NOTIFICATION(OperationType.PROJECT),
    ADD_NOTIFICATION(OperationType.PROJECT),
    DELETE_NOTIFICATION(OperationType.PROJECT),
    PUBLISH_PROJECT(OperationType.PROJECT),
    ADD_ROW(OperationType.TABLE),
    DELETE_ROW(OperationType.TABLE),
    ADD_COLUMN(OperationType.TABLE);

    enum class OperationType {
        PROJECT,
        GRAPH,
        TABLE
    }
}