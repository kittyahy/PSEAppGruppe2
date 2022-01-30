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

package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverManager

import android.util.Log
import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandInfo
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.Delta
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.FetchRequest
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.PostPreview
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.TemplateDetail
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

internal class SendCommandsToServerInParallel {

    private var postPreviewList: List<PostPreview> = listOf(PostPreview())
    private var postDetailList: List<TemplateDetail> = listOf(TemplateDetail(id = 1))
    private var deltaList: Collection<Delta> = listOf(Delta(projectCommand = "ProjectCommand"))
    private var fetchRequestList: Collection<FetchRequest> = listOf(FetchRequest(requestInfo = "FetchRequest"))

    private lateinit var restAPI: RESTAPI
    private lateinit var serverManager: ServerManager

    private var validCommands = mutableListOf<String>()
    private var invalidCommand = "command11" // A command, that will not be send to server (eg, due to a lost internet connection)

    @Before
    fun setup() {
        restAPI = mockk<RESTAPI>()
        every { restAPI.greet() } returns true

        coEvery { restAPI.saveDelta(1, "command1", "") } returns true // use coEvery for mockking suspend functions
        coEvery { restAPI.saveDelta(1, "command2", "") } returns true
        coEvery { restAPI.saveDelta(1, "command3", "") } returns true
        coEvery { restAPI.saveDelta(1, "command4", "") } returns true
        coEvery { restAPI.saveDelta(1, "command5", "") } returns true
        coEvery { restAPI.saveDelta(1, "command6", "") } returns true
        coEvery { restAPI.saveDelta(1, "command7", "") } returns true
        coEvery { restAPI.saveDelta(1, "command8", "") } returns true
        coEvery { restAPI.saveDelta(1, "command9", "") } returns true
        coEvery { restAPI.saveDelta(1, "command10", "") } returns true

        coEvery { restAPI.saveDelta(1, "command11", "") } returns false

        // Create ServerManager with mocked RestAPI
        serverManager = ServerManager(restAPI)

        // Fill validCommands List
        for (i in 1..10) {
            validCommands.add("command$i")
        }
    }


    @Test
    fun sendOneCommand() {
        Assert.assertEquals(listOf(validCommands.elementAt(0)) , serverManager.sendCommandsToServer(1, listOf(validCommands.elementAt(0)), ""))
    }

    @Test
    fun sendEmptyCommand() {
        Assert.assertEquals(listOf<String>(), serverManager.sendCommandsToServer(1, listOf(), ""))
    }

    @Test
    fun sendTenCommands() {
        var commandsToSend = validCommands.toMutableList()
        val commandsSend = serverManager.sendCommandsToServer(1, commandsToSend, "")
        Assert.assertEquals(10, commandsSend.size)
        commandsSend.forEach() {
            Assert.assertTrue(commandsToSend.remove(it))
            Log.d("DEBUGParallel", it)
        }
        Assert.assertTrue(commandsToSend.isEmpty())
    }

    @Test
    fun sendOneCommandError() {
        // The invalid command should not be returned
        Assert.assertEquals(listOf<String>(), serverManager.sendCommandsToServer(1, listOf(invalidCommand), ""))
    }

    @Test
    fun sendCommandsWithOneError() {
        var commandsToSend = validCommands.toMutableList()
        commandsToSend.add(invalidCommand)
        val commandsSend = serverManager.sendCommandsToServer(1, commandsToSend, "")
        Assert.assertEquals(commandsToSend.size-1, commandsSend.size)
        commandsSend.forEach() {
            Assert.assertTrue(commandsToSend.remove(it))
        }
        Assert.assertEquals(1, commandsToSend.size)
    }
}