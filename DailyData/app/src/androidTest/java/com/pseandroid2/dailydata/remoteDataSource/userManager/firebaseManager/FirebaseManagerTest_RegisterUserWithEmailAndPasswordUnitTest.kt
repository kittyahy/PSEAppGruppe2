package com.pseandroid2.dailydata.remoteDataSource.userManager.firebaseManager

import android.util.Log
import org.junit.Assert
import org.junit.Test
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions

class FirebaseManagerTest_RegisterUserWithEmailAndPasswordUnitTest {

    @Test
    fun registerUser() {
        var email = "test@student.kit.edu"
        var password = "PSEistsuper"

        var fm = FirebaseManager()

        val returnParameter = fm.registerUserWithEmailAndPassword(email, password)

        Assert.assertEquals(FirebaseReturnOptions.REGISTERED, returnParameter)
    }
}