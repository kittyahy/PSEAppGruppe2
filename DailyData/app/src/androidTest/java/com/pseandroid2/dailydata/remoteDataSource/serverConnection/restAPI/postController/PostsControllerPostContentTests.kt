package com.pseandroid2.dailydata.remoteDataSource.serverConnection.restAPI.postController

import android.util.Log
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.RESTAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.PostPreviewWrapper
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.TemplateDetailWrapper
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

    private var postID: Int = -1

    @Before
    fun setup() {
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

        val byteArray = byteArrayOfInts(0xA1, 0x2E, 0x38, 0xD4, 0x89, 0xC3)

        postID = restAPI.addPost(
            PostPreviewWrapper(title = "project preview"),
            Pair(
                "project template",
                TemplateDetailWrapper(byteArray)
            ), // Adds quotation marks because the server can't recognize is otherwise as a String (bug)
            listOf(Pair("graph template", TemplateDetailWrapper())), authToken
        )
        Assert.assertTrue(postID > 0)

        // Set Values in companion object for correct teardown
        setTeardown(restAPI, postID, authToken)
    }

    /**
     * Converts Ints into an byte array
     */
    private fun byteArrayOfInts(vararg ints: Int) =
        ByteArray(ints.size) { pos -> ints[pos].toByte() }


    companion object Teardown {
        private var restAPI: RESTAPI? = null
        private var postID: Int = -1
        private var authToken: String = ""

        fun setTeardown(restapi: RESTAPI, postID: Int, authToken: String) {
            restAPI = restapi
            Teardown.postID = postID
            Teardown.authToken = authToken
        }

        @AfterClass
        @JvmStatic
        fun teardown() {
            restAPI!!.removePost(postID, authToken)
            Log.d("Teardown", "Complete")
        }
    }


    @Test
    fun getPostDetail() {
        val templateDetails: List<TemplateDetail> =
            restAPI.getPostDetail(postID, authToken) as List<TemplateDetail>
        Assert.assertNotEquals(emptyList<TemplateDetail>(), templateDetails)
        templateDetails.forEach {
            Assert.assertNotEquals("", it.title)
        }
    }

    @Test
    fun getProjectTemplate() {
        val projectTemplate: String = restAPI.getProjectTemplate(postID, authToken)
        Assert.assertEquals("project template", projectTemplate)
    }

    @Test
    fun getGraphTemplate() {
        val graphTemplate: String = restAPI.getGraphTemplate(fromPost = postID, 1, authToken)
        Assert.assertEquals("graph template", graphTemplate)
    }

    /* TODO: In the quality phase
    @Test
    fun getFromWrongPostID() {
        Assert.assertEquals(
            emptyList<TemplateDetail>(),
            restAPI.getPostDetail(fromPost = -1, authToken) as List<TemplateDetail>
        )
        Assert.assertEquals("", restAPI.getProjectTemplate(-1, authToken))
        Assert.assertEquals("", restAPI.getGraphTemplate(-1, 1, authToken))
    }
    */
}