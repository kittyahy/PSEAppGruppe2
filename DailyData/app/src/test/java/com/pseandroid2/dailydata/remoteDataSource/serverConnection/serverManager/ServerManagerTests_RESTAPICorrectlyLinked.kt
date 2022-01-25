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

internal class ServerManagerTests_RESTAPICorrectlyLinked {

    private var postPreviewList: List<PostPreview> = listOf(PostPreview())
    private var postDetailList: List<TemplateDetail> = listOf(TemplateDetail(id = 1))
    private var deltaList: Collection<Delta> = listOf(Delta(projectCommand = "ProjectCommand"))
    private var fetchRequestList: Collection<FetchRequest> = listOf(FetchRequest(requestInfo = "FetchRequest"))

    private lateinit var restAPI: RESTAPI

    private lateinit var serverManager: ServerManager

    @Before
    fun setup() {
        restAPI = mockk<RESTAPI>()
        every { restAPI.greet() } returns true

        every { restAPI.getAllPostsPreview("")} returns postPreviewList
        every { restAPI.getPostDetail(1,"")} returns postDetailList
        every { restAPI.getProjectTemplate(1, "")} returns "ProjectTemplate"
        every { restAPI.getGraphTemplate(1, 1, "")} returns "GraphTemplate"
        every { restAPI.addPost("", projectTemplate =  Pair("", ""), graphTemplate = listOf(Pair("", "")), "")} returns 1
        every { restAPI.removePost(1, "")} returns true
        every { restAPI.addUser(1, "")} returns true
        every { restAPI.removeUser("", 1, "")} returns true
        every { restAPI.addProject("")} returns 0
        coEvery { restAPI.saveDelta(1, "command1", "") } returns true // use coEvery for mockking suspend functions
        every { restAPI.getDelta(1, "") } returns deltaList
        every { restAPI.providedOldData("", "", LocalDateTime.parse("0001-01-01T00:00"), "", 1, false, "") } returns true
        every { restAPI.getRemoveTime("") } returns LocalDateTime.parse("0001-01-01T00:00")
        every { restAPI.demandOldData(1, "", "") } returns true
        every { restAPI.getFetchRequests(1, "") } returns fetchRequestList

        // Create ServerManager with mocked RestAPI
        serverManager = ServerManager(restAPI)
    }


    @Test
    fun restAPILinked() {
        // Test if serverManager returns the expected outputs
        Assert.assertTrue(serverManager.greet())

        Assert.assertEquals(postPreviewList.elementAt(0), serverManager.getAllPostPreview("").elementAt(0))

        Assert.assertEquals(postDetailList.elementAt(0), serverManager.getPostDetail(1, "").elementAt(0))

        Assert.assertEquals("ProjectTemplate", serverManager.getProjectTemplate(1, ""))

        Assert.assertEquals("GraphTemplate", serverManager.getGraphTemplate(1, 1, ""))

        Assert.assertEquals(1, serverManager.addPost("", projectTemplate =  Pair("", ""), graphTemplate = listOf(Pair("", "")), ""))

        Assert.assertTrue(serverManager.removePost(1, ""))

        Assert.assertTrue(serverManager.addUser(1, ""))

        Assert.assertTrue(serverManager.removeUser("", 1, ""))

        Assert.assertEquals(0, serverManager.addProject(""))

        Assert.assertEquals("command1", serverManager.sendCommandsToServer(1, projectCommands=listOf("command1"), "").elementAt(0))

        // Test if projectCommand lands in the projectCommandQueue
        serverManager.getDeltasFromServer(1, "")
        val delta: Delta = deltaList.elementAt(0)
        val projectCommandInfoInList: ProjectCommandInfo = ProjectCommandInfo(delta.addedToServer, delta.user, delta.isAdmin, delta.projectCommand)
        Assert.assertEquals(projectCommandInfoInList, serverManager.getProjectCommandFromQueue())

        Assert.assertTrue(serverManager.provideOldData("", "", LocalDateTime.parse("0001-01-01T00:00"), "", 1, false, ""))

        Assert.assertEquals(LocalDateTime.parse("0001-01-01T00:00"), serverManager.getRemoveTime(""))

        Assert.assertEquals(true, serverManager.demandOldData(1, "", ""))

        // Test if fetchRequests lands in the fetchRequestQueue
        serverManager.getFetchRequests(1, "")
        Assert.assertEquals(fetchRequestList.elementAt(0), serverManager.getFetchRequestFromQueue())
    }
}