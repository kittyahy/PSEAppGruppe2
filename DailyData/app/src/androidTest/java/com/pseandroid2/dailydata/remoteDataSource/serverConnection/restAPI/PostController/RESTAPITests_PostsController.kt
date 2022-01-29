package com.pseandroid2.dailydata.remoteDataSource.serverConnection.restAPI.PostController

import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.PostPreview
import org.junit.Assert
import org.junit.Test
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import org.junit.Before

class RESTAPITests_PostsController {
    private var restAPI: RESTAPI = RESTAPI()
    private lateinit var authToken: String

    @Before
    fun setup() {
        // Generate valid firebase authentication token
        val fm: FirebaseManager = FirebaseManager(null)
        var email = "test@student.kit.edu"
        var password = "PSEistsuper"

        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email, password))
        authToken = fm.getToken()
    }

    @Test
    fun getAllPostsPreviewIsNotEmpty() {
        // First create a new post, so the post preview is always not empty
        val postID = restAPI.addPost("postPreview1", Pair("projectTemplate", "projectTemplatePreview"),
            listOf(Pair("graphTemplate", "graphTemplatePreview")), authToken)
        Assert.assertNotEquals(-1, postID)

        // Check if post preview is not empty
        var postPreviews = restAPI.getAllPostsPreview(authToken)

        Assert.assertNotEquals(0, postPreviews)
        var postPreviewsList: MutableList<PostPreview>
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

    @Test
    fun addAndRemovePost() {
        val postID: Int = restAPI.addPost("postPreviewTest", Pair("projectTemplate", "projectTemplatePreview"),
            listOf(Pair("graphTemplate", "graphTemplatePreview")), authToken)
        Assert.assertNotEquals(-1, postID)

        Assert.assertTrue(restAPI.removePost(postID, authToken))
    }
}