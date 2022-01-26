package com.pseandroid2.dailydata.remoteDataSource.serverConnection.restAPI

import android.util.Log
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.AddPostParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.RequestParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.PostPreview
import org.junit.Assert
import org.junit.Test
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import org.junit.Before
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

class RESTAPITests_PostsController {
    private var restAPI: RESTAPI = RESTAPI()
    private lateinit var authToken: String

    @Before
    fun setup() {
        // Generate valid firebase authentication token
        val fm: FirebaseManager = FirebaseManager()
        var email = "test@student.kit.edu"
        var password = "PSEistsuper"

        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email, password))
        authToken = fm.getToken()
    }

    @Test
    fun getAllPostsPreviewIsNotEmpty() {
         // TODO REWORK TEST: Cant pass body through @get and @delete
        /*
        var postPreviews: MutableList<PostPreview> = (restAPI.getAllPostsPreview(authToken) as MutableList<PostPreview>)
        Assert.assertNotEquals(0, postPreviews.size)
        Assert.assertNotEquals(null, postPreviews.elementAt(0))
         */
    }

    @Test
    fun addAndRemovePost() {
        /* // TODO: Change addPost return parameter to Integer
        val postID: Int = restAPI.addPost("post preview", "project template",
            listOf("graph template"), authToken)
        Assert.assertNotEquals(-1, postID)

        Assert.assertTrue(restAPI.removePost(postID, authToken))
        */
    }

    @Test
    fun AuthTest() {
        val postID: Int = restAPI.addPost("post preview", "project template",
            listOf("graph template"), authToken)
        restAPI.removePost(postID, authToken)
    }
}