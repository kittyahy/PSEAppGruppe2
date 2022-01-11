package com.pseandroid2.dailydata.remoteDataSource.userManager.firebaseManager

import android.util.Log
import org.junit.Assert
import org.junit.Test
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions

class FirebaseManagerTest_EmptyParameter {

    @Test
    fun getEmail() {
        var email = "test@student.kit.edu"
        var password = "PSEistsuper"

        var fm = FirebaseManager()

        fm.signOut()
        Assert.assertEquals("", fm.getUserID())

        var returnParameter = fm.signInWithEmailAndPassword(email, "")

        Assert.assertEquals(FirebaseReturnOptions.SIGN_IN_FAILED, returnParameter)

        returnParameter = fm.signInWithEmailAndPassword("", password)

        Assert.assertEquals(FirebaseReturnOptions.SIGN_IN_FAILED, returnParameter)

        returnParameter = fm.registerUserWithEmailAndPassword(email, "")

        Assert.assertEquals(FirebaseReturnOptions.REGISTRATION_FAILED, returnParameter)

        returnParameter = fm.registerUserWithEmailAndPassword("", "")

        Assert.assertEquals(FirebaseReturnOptions.REGISTRATION_FAILED, returnParameter)

        returnParameter = fm.registerUserWithEmailAndPassword("", password)

        Assert.assertEquals(FirebaseReturnOptions.REGISTRATION_FAILED, returnParameter)

        returnParameter = fm.registerUserWithEmailAndPassword("", "")

        Assert.assertEquals(FirebaseReturnOptions.REGISTRATION_FAILED, returnParameter)
    }
}