package com.pseandroid2.dailydata.remoteDataSource.serverConnection.restAPI.postController

import android.util.Log
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.PostPreviewWrapper
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.TemplateDetailWrapper
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.PostPreview
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.TemplateDetail
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PostsControllerPostContentTests {
    private val restAPI: RESTAPI = RESTAPI()
    private val serverManager = ServerManager(restAPI)
    private lateinit var authToken: String

    private lateinit var postPreviewsList: MutableList<PostPreview>
    private var postID: Int = -1

    @Before
    fun setup() {
        // Generate valid firebase authentication token
        val fm: FirebaseManager = FirebaseManager(null)
        val email = "test@student.kit.edu"
        val password = "PSEistsuper"

        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email, password))
        authToken = fm.getToken()

        serverManager.deleteAllPostsFromUser(authToken)

        postID = restAPI.addPost(
            PostPreviewWrapper( title = "project preview"),
            Pair("project template", TemplateDetailWrapper()),
            listOf(Pair("graph template", TemplateDetailWrapper())), authToken)
        Assert.assertTrue(postID > 0)

        // Set Values in companion object for correct teardown
        setTeardown(restAPI, postID, authToken)
    }

    companion object Teardown{
        private var restAPI: RESTAPI? = null
        private var postID: Int = -1
        private var authToken: String = ""

        fun setTeardown(restapi: RESTAPI, postID: Int, authToken: String) {
            restAPI = restapi
            Teardown.postID = postID
            Teardown.authToken = authToken
        }

        @AfterClass
        @JvmStatic fun teardown() {
            restAPI!!.removePost(postID, authToken)
            Log.d("Teardown", "Complete")
        }
    }


    @Test
    fun getPostDetail() {
        val templateDetails: List<TemplateDetail> = restAPI.getPostDetail(postID, authToken) as List<TemplateDetail>
        Assert.assertNotEquals(emptyList<TemplateDetail>(), templateDetails)
        templateDetails.forEach() {
            Assert.assertNotEquals("", it.title)
        }
    }

    @Test
    fun getProjectTemplate() {
        val projectTemplate: String = restAPI.getProjectTemplate(postID, authToken)
        Assert.assertEquals("projectTemplate", projectTemplate)
    }

    @Test
    fun getGraphTemplate() {
        val graphTemplate: String = restAPI.getGraphTemplate(postID, 1, authToken)
        Assert.assertEquals("graphTemplate", graphTemplate)
    }

    @Test
    fun getFromWrongPostID() {
        Assert.assertEquals(emptyList<TemplateDetail>(), restAPI.getPostDetail(-1, authToken) as List<TemplateDetail>)
        Assert.assertEquals("", restAPI.getProjectTemplate(-1, authToken))
        Assert.assertEquals("", restAPI.getGraphTemplate(-1, 1, authToken))
    }

    //TODO Tests auskommentieren
}