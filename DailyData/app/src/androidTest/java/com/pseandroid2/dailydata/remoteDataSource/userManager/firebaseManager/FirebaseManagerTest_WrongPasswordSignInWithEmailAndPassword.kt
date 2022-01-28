package com.pseandroid2.dailydata.remoteDataSource.userManager.firebaseManager

import android.util.Log
import org.junit.Assert
import org.junit.Test
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions

class FirebaseManagerTest_WrongPasswordSignInWithEmailAndPassword {

    @Test
    fun registerUser() {
        var email = "test@student.kit.edu"
        var password = "WrongPassword"

        var fm = FirebaseManager(null)

        fm.signOut()
        val returnParameter = fm.signInWithEmailAndPassword(email, password)

        Assert.assertEquals(FirebaseReturnOptions.SIGN_IN_FAILED, returnParameter)
    }
}