package com.pseandroid2.dailydata.remoteDataSource.userManager.firebaseManager

import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FirebaseUserData {
    private val fm = FirebaseManager(null)
    private var email = "test@student.kit.edu"
    private var password = "PSEistsuper"

    @ExperimentalCoroutinesApi
    @Before
    fun setup() = runBlocking {
        // SignOut
        Assert.assertEquals(FirebaseReturnOptions.SINGED_OUT, fm.signOut())
    }

    @ExperimentalCoroutinesApi
    @After
    fun signOut() = runTest {
        Assert.assertEquals(FirebaseReturnOptions.SINGED_OUT, fm.signOut())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getAuthToken() = runTest {
        // SignIn User
        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email, password))
        Assert.assertNotEquals("", fm.getToken())

        // SignOut Logged in User
        Assert.assertEquals(FirebaseReturnOptions.SINGED_OUT, fm.signOut())
        Assert.assertEquals("", fm.getToken())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getEmail() = runTest {
        Assert.assertEquals("", fm.getUserEMail())

        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email, password))
        Assert.assertEquals("test@student.kit.edu", fm.getUserEMail())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getUserID() = runTest {
        Assert.assertEquals("", fm.getUserID())

        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email, password))
        Assert.assertEquals("4hpJh32YaAWrAYoVvo047q7Ey183", fm.getUserID())
    }
}
