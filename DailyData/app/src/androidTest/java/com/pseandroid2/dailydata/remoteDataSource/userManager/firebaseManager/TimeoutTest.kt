package com.pseandroid2.dailydata.remoteDataSource.userManager.firebaseManager

import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TimeoutTest {
    private val fm = FirebaseManager(0)
    private var email = "test@student.kit.edu"
    private var password = "PSEistsuper"

    @Before
    fun setup() {
        fm.signOut()
    }

    @Test
    fun registerTimeout() {
        Assert.assertEquals(FirebaseReturnOptions.TIMEOUT, fm.registerUserWithEmailAndPassword(email, password))
    }

    @Test
    fun emailSignInTimeout() {
        Assert.assertEquals(FirebaseReturnOptions.TIMEOUT, fm.signInWithEmailAndPassword(email, password))
    }
}