/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/

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