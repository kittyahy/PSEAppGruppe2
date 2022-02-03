package com.pseandroid2.dailydata.remoteDataSource.rdsAPI

import android.graphics.Bitmap
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.PostPreviewWrapper
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.TemplateDetailWrapper
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.FetchRequest
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.PostPreview
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns.TemplateDetail
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import com.pseandroid2.dailydata.remoteDataSource.userManager.SignInTypes
import com.pseandroid2.dailydata.remoteDataSource.userManager.UserAccount
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class RDSAPI_CorrectlyLinked {

    private lateinit var rdsAPI: RemoteDataSourceAPI

    private var postPreviewList: List<PostPreview> = listOf(PostPreview())
    private var postDetailList: List<TemplateDetail> = listOf(TemplateDetail())
    private var sendCommandsList: Collection<String> = listOf("ProjectCommand")
    private var deltaList: Collection<String> = listOf("Delta")
    private var fetchRequestList: Collection<FetchRequest> = listOf(FetchRequest(requestInfo = "FetchRequest"))

    private lateinit var bitmap: Bitmap

    @Before
    fun setup() {
        bitmap = mockk()

        val serverManager = mockk<ServerManager>()

        every { serverManager.greet() } returns true
        every { serverManager.getAllPostPreview("")} returns postPreviewList
        every { serverManager.getPostDetail(1,"")} returns postDetailList
        every { serverManager.getProjectTemplate(1, "")} returns "ProjectTemplate"
        every { serverManager.getGraphTemplate(1, 1, "")} returns "GraphTemplate"
        every { serverManager.addPost(postPreview = Pair(bitmap, ""), projectTemplate = Pair("", Pair(bitmap, "")), graphTemplates = listOf(Pair("", Pair(bitmap, ""))), authToken =  "")} returns 1
        every { serverManager.removePost(1, "")} returns true
        every { serverManager.addUser(1, "")} returns "project details"
        every { serverManager.removeUser("", 1, "")} returns true
        every { serverManager.addProject("", "project details")} returns 0
        every { serverManager.getProjectParticipants("", 1)} returns listOf("")
        every { serverManager.isProjectParticipant("", 1, "")} returns true
        every { serverManager.getProjectAdmin("", 1)} returns ""

        every { serverManager.sendCommandsToServer(1, emptyList(), "") } returns sendCommandsList // use coEvery for mockking suspend functions
        every { serverManager.provideOldData("", "", LocalDateTime.parse("0001-01-01T00:00"), "", 1, false, "") } returns true
        every { serverManager.getRemoveTime("") } returns 42
        every { serverManager.demandOldData(1, "", "") } returns true

        // mock UserAccount
        val userAccount = mockk<UserAccount>()
        every { userAccount.registerUser("", "", SignInTypes.EMAIL) } returns FirebaseReturnOptions.REGISTERED
        every { userAccount.signInUser("", "", SignInTypes.EMAIL) } returns FirebaseReturnOptions.SINGED_IN
        every { userAccount.signOut() } returns FirebaseReturnOptions.SINGED_OUT
        every { userAccount.getUserID() } returns "userID"
        every { userAccount.getUserName() } returns "userName"
        every { userAccount.getUserEMail() } returns "userEmail"
        every { userAccount.getUserPhotoUrl() } returns "photo"
        every { userAccount.getToken() } returns ""

        // Create RDS with mocked serverManager and mocked FirebaseManager
        rdsAPI = RemoteDataSourceAPI(userAccount, serverManager)
    }

    @Test
    fun serverManagerCorrectlyLinked() {
        Assert.assertTrue(rdsAPI.connectionToServerPossible())
        Assert.assertEquals(postPreviewList.elementAt(0), rdsAPI.getPostPreviews().elementAt(0))
        Assert.assertEquals(postDetailList.elementAt(0), rdsAPI.getPostDetail(1).elementAt(0))
        Assert.assertEquals("ProjectTemplate", rdsAPI.getProjectTemplate(1))
        Assert.assertEquals("GraphTemplate", rdsAPI.getGraphTemplate(1, 1))
        Assert.assertEquals(1, rdsAPI.uploadPost(postPreview = Pair(bitmap, ""), projectTemplate = Pair("", Pair(bitmap, "")), graphTemplates = listOf(Pair("", Pair(bitmap, "")))))
        Assert.assertTrue(rdsAPI.removePost(1))
        Assert.assertEquals("project details", rdsAPI.joinProject(1))
        Assert.assertTrue(rdsAPI.removeUser("", 1))
        Assert.assertEquals(0, rdsAPI.createNewOnlineProject("project details"))
        Assert.assertEquals(listOf(""), rdsAPI.getProjectParticipants(1))
        Assert.assertEquals(true, rdsAPI.isProjectParticipant("", 1, ""))
        Assert.assertEquals("", rdsAPI.getProjectAdmin(1))
        Assert.assertEquals(sendCommandsList.elementAt(0), rdsAPI.sendCommandsToServer(1, emptyList()).elementAt(0))
        Assert.assertTrue(rdsAPI.provideOldData("", "", LocalDateTime.parse("0001-01-01T00:00"), "", 1, false))
        Assert.assertEquals(42, rdsAPI.getRemoveTime())
        Assert.assertTrue(rdsAPI.demandOldData(1, ""))
    }

    @Test
    fun userAccountCorrectlyLinked() {
        Assert.assertEquals(FirebaseReturnOptions.REGISTERED, rdsAPI.registerUser("", "", SignInTypes.EMAIL))
        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, rdsAPI.signInUser("", "", SignInTypes.EMAIL))
        Assert.assertEquals(FirebaseReturnOptions.SINGED_OUT, rdsAPI.signOut())
        Assert.assertEquals("userID", rdsAPI.getUserID())
        Assert.assertEquals("userName", rdsAPI.getUserName())
        Assert.assertEquals("userEmail", rdsAPI.getUserEMail())
        Assert.assertEquals("photo", rdsAPI.getUserPhotoUrl())
    }
}