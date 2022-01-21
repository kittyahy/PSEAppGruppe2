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
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.time.LocalDateTime

internal class ServerManagerTests_RESTAPICorrectlyLinked {




    @Test
    fun restAPILinked() {
        var postPreviewList: List<String> = listOf("PostPreview")
        var postDetailList: List<String> = listOf("PostDetail")
        var deltaList: List<ProjectCommandInfo> = listOf(ProjectCommandInfo())
        var fetchRequestList: List<String> = listOf("FetchRequest")

        var restAPI: RESTAPI = mock<RESTAPI>()
        every { restAPI.greet() } return true
        every { restAPI.getAllPostsPreview()} return postPreviewList
        every { restAPI.getPostDetail()} return postDetailList
        every { restAPI.getProjectTemplate()} return "ProjectTemplate"
        every { restAPI.getGraphTemplate()} return "GraphTemplate"
        every { restAPI.addPost()} return true
        every { restAPI.removePost()} return true
        every { restAPI.addUser()} return true
        every { restAPI.removeUser()} return true
        every { restAPI.addProject()} return 0
        every { restAPI.saveDelta() } return true
        every { restAPI.getDelta() } return deltaList
        every { restAPI.providedOldData } return true
        every { restAPI.getRemoveTime() } return LocalDateTime.parse("0001-01-01T00:00")
        every { restAPI.demandOldData() } return true
        every { restAPI.getFetchRequests() } return fetchRequestList

        // Create ServerManager with mocked RestAPI
        val serverManager = ServerManager(restAPI)
        val authToken = "";

        // Test if serverManager returns the expected outputs
        Assert.assertTrue(serverManager.greet())

        Assert.assertEquals("PostPreview", serverManager.getAllPostPreview(authToken).elementAt(0))

        Assert.assertEquals("PostDetail", serverManager.getPostDetail(1, authToken).elementAt(0))

        Assert.assertEquals("ProjectTemplate", serverManager.getProjectTemplate(1, authToken))

        Assert.assertEquals("GraphTemplate", serverManager.getGraphTemplate(1, 1, authToken))

        Assert.assertTrue(serverManager.addPost("", "", listOf(""), authToken))

        Assert.assertTrue(serverManager.removePost(1, authToken))

        Assert.assertTrue(serverManager.addUser("", 1, authToken))

        Assert.assertTrue(serverManager.removeUser("", 1, authToken))

        Assert.assertEquals(0, serverManager.addProject(authToken))

        Assert.assertEquals("command1", serverManager.sendCommandsToServer(1, listOf("command1"), authToken).elementAt(0))

        // Test if projectCommand lands in the projectCommandQueue
        serverManager.getDeltasFromServer(1, authToken)
        Assert.assertEquals(deltaList.get(0), serverManager.getProjectCommandFromQueue())

        Assert.assertTrue(serverManager.provideOldData("ProjectCommand", "", LocalDateTime.parse("0001-01-01T00:00"), "", 1, false, authToken))

        Assert.assertEquals(LocalDateTime.parse("0001-01-01T00:00"), serverManager.getRemoveTime(authToken))

        Assert.assertEquals(true, serverManager.demandOldData(1, "", authToken))

        // Test if fetchRequests lands in the fetchRequestQueue
        serverManager.getFetchRequests(1, authToken)
        Assert.assertEquals("FetchRequest", serverManager.getFetchRequestFromQueue())
    }
}