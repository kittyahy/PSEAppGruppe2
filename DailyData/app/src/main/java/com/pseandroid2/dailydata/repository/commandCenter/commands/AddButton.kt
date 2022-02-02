package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button

class AddButton(val id: Int, val button: Button) : ProjectCommand() {
    override val publishable: Boolean = false

    override suspend fun execute(
        appDataBase: AppDataBase,
        remoteDataSourceAPI: RemoteDataSourceAPI,
        publishQueue: PublishQueue
    ) {
        val uiElement = button.toDBEquivalent()
        appDataBase.uiElementDAO().insertUIElement(id, button.columnId, uiElement)
        super.execute(appDataBase, remoteDataSourceAPI, publishQueue)
    }

}
