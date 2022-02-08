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

import com.pseandroid2.dailydata.remoteDataSource.queue.ProjectCommandInfo
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.PostPreviewWrapper
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.TemplateDetailWrapper
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.Delta
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.FetchRequest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import java.time.LocalDateTime
@Ignore
internal class ServerManagerIsRESTAPICorrectlyLinkedTests {
/*
    private var deltaList: Collection<Delta> = listOf(Delta(projectCommand = "ProjectCommand"))
    private var fetchRequestList: Collection<FetchRequest> =
        listOf(FetchRequest(requestInfo = "FetchRequest"))

    private lateinit var restAPI: RESTAPI

    private lateinit var serverManager: ServerManager

    @Before
    fun setup() {
        restAPI = mockk()
        every { restAPI.greet() } returns true

        every { restAPI.getProjectTemplate(1, "") } returns "ProjectTemplate"
        every { restAPI.getGraphTemplate(1, 1, "") } returns "GraphTemplate"
        every {
            restAPI.addPost(
                PostPreviewWrapper(),
                Pair("", TemplateDetailWrapper()),
                listOf(Pair("", TemplateDetailWrapper())),
                ""
            )
        } returns 1
        every { restAPI.removePost(1, "") } returns true
        every { restAPI.addUser(1, "") } returns "project details"
        every { restAPI.removeUser("", 1, "") } returns true
        every { restAPI.addProject("", "project details") } returns 0
        every { restAPI.getProjectParticipants("", 1) } returns listOf("")
        every { restAPI.getProjectParticipants("user", 1) } returns listOf("user", "otherUser")
        every { restAPI.getProjectAdmin("", 1) } returns ""
        coEvery {
            restAPI.saveDelta(
                1,
                "command1",
                ""
            )
        } returns true // use coEvery for mockking suspend functions
        every { restAPI.getDelta(1, "") } returns deltaList
        every {
            restAPI.provideOldData(
                "",
                "",
                LocalDateTime.parse("0001-01-01T00:00"),
                "",
                1,
                false,
                ""
            )
        } returns true
        every { restAPI.getRemoveTime("") } returns 42
        every { restAPI.demandOldData(1, "", "") } returns true
        every { restAPI.getFetchRequests(1, "") } returns fetchRequestList

        // Create ServerManager with mocked RestAPI
        serverManager = ServerManager(restAPI)
    }


    @Test
    fun restAPILinked() {
        // Test if serverManager returns the expected outputs
        Assert.assertTrue(serverManager.connectionToServerPossible())

        Assert.assertEquals("ProjectTemplate", serverManager.getProjectTemplate(1, ""))

        Assert.assertEquals("GraphTemplate", serverManager.getGraphTemplate(1, 1, ""))

        Assert.assertTrue(serverManager.removePost(1, ""))

        Assert.assertEquals("project details", serverManager.addUser(1, ""))

        Assert.assertTrue(serverManager.removeUser("", 1, ""))

        Assert.assertEquals(0, serverManager.addProject("", "project details"))

        Assert.assertEquals(listOf(""), serverManager.getProjectParticipants("", 1))

        Assert.assertTrue(serverManager.isProjectParticipant("user", 1, "user"))

        Assert.assertEquals("", serverManager.getProjectAdmin("", 1))

        Assert.assertEquals(
            "command1",
            serverManager.sendCommandsToServer(1, projectCommands = listOf("command1"), "")
                .elementAt(0)
        )

        // Test if projectCommand lands in the projectCommandQueue
        serverManager.getProjectCommandsFromServer(1, "")
        val delta: Delta = deltaList.elementAt(0)
        val projectCommandInfoInList =
            ProjectCommandInfo(delta.user, delta.admin, delta.projectCommand, delta.addedToServerS)

        Assert.assertEquals(projectCommandInfoInList, serverManager.getProjectCommandFromQueue())

        Assert.assertTrue(
            serverManager.provideOldData(
                "",
                "",
                LocalDateTime.parse("0001-01-01T00:00"),
                "",
                1,
                false,
                ""
            )
        )

        Assert.assertEquals(42, serverManager.getRemoveTime(""))

        Assert.assertEquals(true, serverManager.demandOldData(1, "", ""))

        // Test if fetchRequests lands in the fetchRequestQueue
        serverManager.getFetchRequests(1, "")
        Assert.assertEquals(fetchRequestList.elementAt(0), serverManager.getFetchRequestFromQueue())
    }*/
}