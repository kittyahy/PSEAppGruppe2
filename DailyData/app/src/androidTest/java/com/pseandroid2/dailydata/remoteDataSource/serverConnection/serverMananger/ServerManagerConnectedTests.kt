package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverMananger

import android.graphics.Bitmap
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.forRepoReturns.PostPreviewWithPicture
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ServerManagerConnectedTests {
    private val serverManager: ServerManager = ServerManager(RESTAPI())
    private val fm = FirebaseManager(null)

    private val email = "test@student.kit.edu"
    private val password = "PSEistsuper"
    private val userID = "4hpJh32YaAWrAYoVvo047q7Ey183"
    private var authToken: String = ""

    private var projectID: Long = -1
    private var postID: Int = -1

    private val bitmap = Bitmap.createBitmap(
        1,
        1,
        Bitmap.Config.ARGB_8888
    )

    @Before
    fun setup() = runBlocking {
        Assert.assertEquals(
            FirebaseReturnOptions.SINGED_IN,
            fm.signInWithEmailAndPassword(email, password)
        )
        authToken = fm.getToken()
        // Create new project
        projectID = serverManager.addProject(authToken, "project details")
        Assert.assertTrue(projectID > 0)

        serverManager.deleteAllPostsFromUser(authToken)


        // create a new post, so the post preview is always not empty
        postID = serverManager.addPost(
            Pair(bitmap, "post preview to test"),
            Pair("project template", Pair(bitmap, "project template title to test")),
            listOf(Pair("graph template", Pair(bitmap, "graph template title to test"))), authToken
        )
        Assert.assertTrue(postID > 0)

        setTeardown(
            serverManager,
            projectID,
            authToken,
            userID,
            postID
        )
    }

    companion object Teardown {
        private var serverManager: ServerManager? = null
        private var projectID: Long = -1
        private var authToken: String = ""
        private var userToRemove1: String = ""
        private var postID: Int = -1
        fun setTeardown(
            serverManager: ServerManager,
            projectID: Long,
            authToken: String,
            userToRemove1: String,
            postID: Int
        ) {
            Teardown.serverManager = serverManager
            Teardown.projectID = projectID
            Teardown.authToken = authToken
            Teardown.userToRemove1 = userToRemove1
            Teardown.postID = postID
        }

        @AfterClass
        @JvmStatic
        fun teardown() {
            runBlocking {
                serverManager?.removePost(postID, authToken)
                // Remove all users from project so that the project gets removed
                serverManager?.removeUser(userToRemove1, projectID, authToken)
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getAllPostPreviews() = runTest {
        // Check if post preview is not empty
        val postPreviews = serverManager.getAllPostPreview(authToken) as MutableList
        Assert.assertNotEquals(0, postPreviews)

        // Find uploaded post preview
        var postPreview: PostPreviewWithPicture? = null
        postPreviews.forEach {
            if (it.preview == "post preview to test") {
                postPreview = it
            }
        }
        Assert.assertNotEquals(null, postPreview)
        Assert.assertEquals(postID, postPreview?.id)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getPostDetail() = runTest {
        val postPreview = serverManager.getPostDetail(postID, authToken)
        Assert.assertEquals("project template title to test", postPreview.elementAt(0).title)
        Assert.assertEquals("graph template title to test", postPreview.elementAt(1).title)
    }
}