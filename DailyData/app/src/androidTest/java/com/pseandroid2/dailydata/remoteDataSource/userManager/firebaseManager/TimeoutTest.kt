package com.pseandroid2.dailydata.remoteDataSource.userManager.firebaseManager

import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TimeoutTest {
    private val fm = FirebaseManager(0)
    private var email = "test@student.kit.edu"
    private var password = "PSEistsuper"

    @ExperimentalCoroutinesApi
    @Test
    fun registerTimeout() = runTest {
        Assert.assertEquals(FirebaseReturnOptions.TIMEOUT, fm.registerUserWithEmailAndPassword(email, password))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emailSignInTimeout() = runTest {
        Assert.assertEquals(FirebaseReturnOptions.TIMEOUT, fm.signInWithEmailAndPassword(email, password))
    }
}
