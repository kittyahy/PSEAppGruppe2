package com.pseandroid2.dailydata.remoteDataSource.userManager.firebaseManager

import android.util.Log
import org.junit.Assert
import org.junit.Test
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions

class FirebaseManagerTest_SignOut {

    @Test
    fun registerUser() {
        var fm = FirebaseManager()

        // SignOut when no User is Connected
        var returnParameter1 =  fm.signOut()
        Assert.assertEquals(FirebaseReturnOptions.SINGED_OUT, returnParameter1)
        Assert.assertEquals("", fm.getUserID())

        // SignIn User
        var email = "test@student.kit.edu"
        var password = "PSEistsuper"
        val returnParameter2 = fm.signInWithEmailAndPassword(email, password)
        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, returnParameter2)
        Assert.assertNotEquals("", fm.getUserID())

        // SignOut Logged in User
        var returnParameter3 =  fm.signOut()
        Assert.assertEquals(FirebaseReturnOptions.SINGED_OUT, returnParameter3)
        Assert.assertEquals("", fm.getUserID())
    }
}