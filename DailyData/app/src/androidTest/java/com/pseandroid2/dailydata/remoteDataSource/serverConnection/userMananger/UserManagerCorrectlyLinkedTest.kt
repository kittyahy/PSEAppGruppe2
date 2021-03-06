package com.pseandroid2.dailydata.remoteDataSource.serverConnection.userMananger

import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import com.pseandroid2.dailydata.remoteDataSource.userManager.SignInTypes
import com.pseandroid2.dailydata.remoteDataSource.userManager.UserAccount
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/*
class UserManagerCorrectlyLinkedTest {

    private val fm: FirebaseManager = FirebaseManager(null)
    private val userAccount = UserAccount(fm)
    private lateinit var mockedFM: FirebaseManager
    private lateinit var mockedUserAccount: UserAccount

    private val email = "test@student.kit.edu"
    private val password = "PSEistsuper"

    @Before
    fun setup() {
        Assert.assertTrue(userAccount.signOut().success) // Signs out user, if there was still one singed in

        // mock fm to test if the registration is linked correctly

        mockedFM = mockk()
        every { mockedFM.registerUserWithEmailAndPassword("", "") } returns FirebaseReturnOptions.REGISTERED
        every { mockedFM.getUserID() } returns "userID"
        every { mockedFM.getUserName() } returns "userName"
        every { mockedFM.getUserEMail() } returns "userEmail"
        every { mockedFM.getUserPhotoUrl() } returns "userPhoto"
        every { mockedFM.getToken() } returns "authToken"
        mockedUserAccount = UserAccount(mockedFM)

    }

    @Test
    fun userAccountCorrectlyLinked() {

        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, userAccount.signInUser(email, password, SignInTypes.EMAIL))
        Assert.assertEquals("userID", mockedUserAccount.getUserID())
        Assert.assertEquals("userName", mockedUserAccount.getUserName())
        Assert.assertEquals("userEmail", mockedUserAccount.getUserEMail())
        Assert.assertEquals("userPhoto", mockedUserAccount.getUserPhotoUrl())
        Assert.assertEquals("authToken", mockedUserAccount.getToken())
        Assert.assertEquals(FirebaseReturnOptions.SINGED_OUT, userAccount.signOut())

        Assert.assertEquals(FirebaseReturnOptions.REGISTERED, mockedUserAccount.registerUser("", "", SignInTypes.EMAIL))
    }
}*/
