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
