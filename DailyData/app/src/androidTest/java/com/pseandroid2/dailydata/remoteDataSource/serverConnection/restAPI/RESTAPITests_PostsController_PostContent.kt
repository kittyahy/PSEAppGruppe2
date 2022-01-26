package com.pseandroid2.dailydata.remoteDataSource.serverConnection.restAPI

import android.util.Log
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.PostPreview
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.TemplateDetail
import org.junit.Assert
import org.junit.Test
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import com.pseandroid2.dailydata.util.ui.Post
import org.junit.AfterClass
import org.junit.Before
import java.lang.Exception
import java.net.IDN

class RESTAPITests_PostsController_PostContent {

    private var restAPI: RESTAPI = RESTAPI()
    private lateinit var authToken: String

    private lateinit var postPreviewsList: MutableList<PostPreview>
    private var postID: Int = -1

    @Before
    fun setup() {
        // Generate valid firebase authentication token
        val fm: FirebaseManager = FirebaseManager()
        var email = "test@student.kit.edu"
        var password = "PSEistsuper"

        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email, password))
        authToken = fm.getToken()

        postID = restAPI.addPost("postPreview1", Pair("projectTemplate", "projectTemplatePreview"),
            listOf(Pair("graphTemplate", "graphTemplatePreview")), authToken)

        Assert.assertNotEquals(-1, postID)


        // Set Values in companion object for correct teardown
        Log.d("Teardown", "Setup")
        setTeardown(restAPI, postID, authToken)
    }

    companion object Teardown{
        private var restAPI: RESTAPI? = null
        private var postID: Int = -1
        private var authToken: String = ""

        fun setTeardown(restapi: RESTAPI, postID: Int, authToken: String) {
            restAPI = restapi
            this.postID = postID
            this.authToken = authToken
        }

        @AfterClass @JvmStatic fun teardown() {
            Assert.assertTrue(restAPI!!.removePost(postID, authToken))
            Log.d("Teardown", "Complete")
        }
    }


    @Test
    fun getPostDetail() {
        val templateDetails: List<TemplateDetail> = restAPI.getPostDetail(postID, authToken) as List<TemplateDetail>
        Assert.assertNotEquals(emptyList<TemplateDetail>(), templateDetails)
        templateDetails.forEach() {
            Assert.assertNotEquals("", it.detail)
        }
    }

    @Test
    fun getProjectTemplate() {
        val projectTemplate: String = restAPI.getProjectTemplate(postID, authToken)
        Assert.assertNotEquals("", projectTemplate)
    }

    @Test
    fun getGraphTemplate() {
        val graphTemplate: String = restAPI.getGraphTemplate(postID, 1, authToken)
        Assert.assertNotEquals("", graphTemplate)
    }
}