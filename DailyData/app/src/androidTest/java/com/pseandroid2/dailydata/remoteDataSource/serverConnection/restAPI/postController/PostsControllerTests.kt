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

package com.pseandroid2.dailydata.remoteDataSource.serverConnection.restAPI.postController

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.URLs
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.PostPreviewWrapper
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.TemplateDetailWrapper
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.PostPreview
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class PostsControllerTests {
    private var restAPI: RESTAPI = RESTAPI(URLs.testServer_BASE_URL)
    private val serverManager = ServerManager(restAPI)
    private lateinit var authToken: String

    @Before
    fun setup() = runBlocking {
        Assert.assertTrue(restAPI.clearServer())
        // Generate valid firebase authentication token
        val fm = FirebaseManager(null)
        val email = "test@student.kit.edu"
        val password = "PSEistsuper"

        Assert.assertEquals(
            FirebaseReturnOptions.SINGED_IN,
            fm.signInWithEmailAndPassword(email, password)
        )
        authToken = fm.getToken()

        serverManager.deleteAllPostsFromUser(authToken)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getAllPostsPreviewIsNotEmpty() = runTest {
        // First create a new post, so the post preview is always not empty
        val postID = restAPI.addPost(
            PostPreviewWrapper(),
            Pair("project template", TemplateDetailWrapper()),
            listOf(Pair("graph template", TemplateDetailWrapper())), authToken
        )
        Assert.assertNotEquals(-1, postID)

        // Check if post preview is not empty
        val postPreviews = restAPI.getAllPostsPreview(authToken)

        Assert.assertNotEquals(0, postPreviews)
        val postPreviewsList: MutableList<PostPreview>
        if (postPreviews.isNotEmpty()) {
            postPreviewsList = postPreviews as MutableList<PostPreview>
            Assert.assertNotEquals(0, postPreviewsList.size)
        }

        Assert.assertNotEquals(null, postPreviews.elementAt(0))

        // Delete added post
        if (postID > 0) {
            Assert.assertTrue(restAPI.removePost(postID, authToken))
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun addAndRemovePost() = runTest {
        val postID: Int = restAPI.addPost(
            PostPreviewWrapper(),
            Pair("project template", TemplateDetailWrapper()),
            listOf(Pair("graph template", TemplateDetailWrapper())), authToken
        )
        Assert.assertTrue(postID > 0)

        Assert.assertTrue(restAPI.removePost(postID, authToken))
    }
}
