package com.pseandroid2.dailydata.repository.commandCenter.commands

import android.util.Log
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Row
import com.pseandroid2.dailydata.util.Consts.LOG_TAG
import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalTime

class ShadowingTest() {
    @Test
    fun test() {
        val addRow: ProjectCommand = AddRow(1, Row(1, listOf("")))
        val addNotification: ProjectCommand =
            AddNotification(1, Notification(1, "", LocalTime.now()))
        set(addRow)
        set(addNotification)
        assertTrue(
            addRow.publish(mockk<RepositoryViewModelAPI>(), mockk<PublishQueue>())
        )
        assertTrue(
            !(addNotification.publish(mockk<RepositoryViewModelAPI>(), mockk<PublishQueue>())
                    )
        )
    }

    private fun set(projectCommand: ProjectCommand) {
        projectCommand.cameFromServer = false
    }
}