package com.pseandroid2.dailydata.remoteDataSource.rdsAPI

import android.graphics.Bitmap
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.ServerManager
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.forRepoReturns.PostPreviewWithPicture
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.forRepoReturns.TemplateDetailWithPicture
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import com.pseandroid2.dailydata.remoteDataSource.userManager.SignInTypes
import com.pseandroid2.dailydata.remoteDataSource.userManager.UserAccount
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class RDSAPICorrectlyLinkedTests {

    private lateinit var rdsAPI: RemoteDataSourceAPI

    private val bitmap = mockk<Bitmap>()
    private var postPreviewList: List<PostPreviewWithPicture> =
        listOf(PostPreviewWithPicture(previewPicture = bitmap))
    private var postDetailList: List<TemplateDetailWithPicture> =
        listOf(TemplateDetailWithPicture(detailImage = bitmap))
    private var sendCommandsList: Collection<String> = listOf("ProjectCommand")

    @Before
    fun setup() {
        val serverManager = mockk<ServerManager>()

        coEvery { serverManager.connectionToServerPossible() } returns true
        coEvery { serverManager.getAllPostPreview("") } returns postPreviewList
        coEvery { serverManager.getPostDetail(1, "") } returns postDetailList
        coEvery { serverManager.getProjectTemplate(1, "") } returns "ProjectTemplate"
        coEvery { serverManager.getGraphTemplate(1, 1, "") } returns "GraphTemplate"
        coEvery {
            serverManager.addPost(
                postPreview = Pair(bitmap, ""),
                projectTemplate = Pair("", Pair(bitmap, "")),
                graphTemplates = listOf(Pair("", Pair(bitmap, ""))),
                authToken = ""
            )
        } returns 1
        coEvery { serverManager.removePost(1, "") } returns true
        coEvery { serverManager.addUser(1, "") } returns "project details"
        coEvery { serverManager.removeUser("", 1, "") } returns true
        coEvery { serverManager.addProject("", "project details") } returns 0
        coEvery { serverManager.getProjectParticipants("", 1) } returns listOf("")
        coEvery { serverManager.isProjectParticipant("", 1, "") } returns true
        coEvery { serverManager.getProjectAdmin("", 1) } returns ""

        coEvery {
            serverManager.sendCommandsToServer(
                1,
                emptyList(),
                ""
            )
        } returns sendCommandsList // use coEvery for mockking suspend functions
        coEvery {
            serverManager.provideOldData(
                "",
                "",
                LocalDateTime.parse("0001-01-01T00:00"),
                "",
                1,
                false,
                ""
            )
        } returns true
        coEvery { serverManager.getRemoveTime("") } returns 42
        coEvery { serverManager.demandOldData(1, "", "") } returns true

        // mock UserAccount
        val userAccount = mockk<UserAccount>()
        coEvery {
            userAccount.registerUser(
                "",
                "",
                SignInTypes.EMAIL
            )
        } returns FirebaseReturnOptions.REGISTERED
        coEvery {
            userAccount.signInUser(
                "",
                "",
                SignInTypes.EMAIL
            )
        } returns FirebaseReturnOptions.SINGED_IN
        coEvery { userAccount.signOut() } returns FirebaseReturnOptions.SINGED_OUT
        coEvery { userAccount.getUserID() } returns "userID"
        coEvery { userAccount.getUserName() } returns "userName"
        coEvery { userAccount.getUserEMail() } returns "userEmail"
        coEvery { userAccount.getUserPhotoUrl() } returns "photo"
        coEvery { userAccount.getToken() } returns ""

        // Create RDS with mocked serverManager and mocked FirebaseManager
        rdsAPI = RemoteDataSourceAPI(userAccount, serverManager)
    }

    @Test
    fun serverManagerCorrectlyLinked() = runBlocking {
        Assert.assertTrue(rdsAPI.connectionToServerPossible())
        Assert.assertEquals(postPreviewList.elementAt(0), rdsAPI.getPostPreviews().elementAt(0))
        Assert.assertEquals(postDetailList.elementAt(0), rdsAPI.getPostDetail(1).elementAt(0))
        Assert.assertEquals("ProjectTemplate", rdsAPI.getProjectTemplate(1))
        Assert.assertEquals("GraphTemplate", rdsAPI.getGraphTemplate(1, 1))
        Assert.assertEquals(
            1,
            rdsAPI.uploadPost(
                postPreview = Pair(bitmap, ""),
                projectTemplate = Pair("", Pair(bitmap, "")),
                graphTemplates = listOf(Pair("", Pair(bitmap, "")))
            )
        )
        Assert.assertTrue(rdsAPI.removePost(1))
        Assert.assertEquals("project details", rdsAPI.joinProject(1))
        Assert.assertTrue(rdsAPI.removeUser("", 1))
        Assert.assertEquals(0, rdsAPI.createNewOnlineProject("project details"))
        Assert.assertEquals(listOf(""), rdsAPI.getProjectParticipants(1))
        Assert.assertEquals(true, rdsAPI.isProjectParticipant("", 1, ""))
        Assert.assertEquals("", rdsAPI.getProjectAdmin(1))
        Assert.assertEquals(
            sendCommandsList.elementAt(0),
            rdsAPI.sendCommandsToServer(1, emptyList()).elementAt(0)
        )
        Assert.assertTrue(
            rdsAPI.provideOldData(
                "",
                "",
                LocalDateTime.parse("0001-01-01T00:00"),
                "",
                1,
                false
            )
        )
        Assert.assertEquals(42, rdsAPI.getRemoveTime())
        Assert.assertTrue(rdsAPI.demandOldData(1, ""))
    }

    @Test
    fun userAccountCorrectlyLinked() = runBlocking {
        Assert.assertEquals(
            FirebaseReturnOptions.REGISTERED,
            rdsAPI.registerUser("", "", SignInTypes.EMAIL)
        )
        Assert.assertEquals(
            FirebaseReturnOptions.SINGED_IN,
            rdsAPI.signInUser("", "", SignInTypes.EMAIL)
        )
        Assert.assertEquals(FirebaseReturnOptions.SINGED_OUT, rdsAPI.signOut())
        Assert.assertEquals("userID", rdsAPI.getUserID())
        Assert.assertEquals("userName", rdsAPI.getUserName())
        Assert.assertEquals("userEmail", rdsAPI.getUserEMail())
        Assert.assertEquals("photo", rdsAPI.getUserPhotoUrl())
    }
}