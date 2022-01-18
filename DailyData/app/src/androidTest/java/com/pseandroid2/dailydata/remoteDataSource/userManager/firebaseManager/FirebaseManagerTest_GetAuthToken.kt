package com.pseandroid2.dailydata.remoteDataSource.userManager.firebaseManager

import android.util.Log
import org.junit.Assert
import org.junit.Test
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions

class FirebaseManagerTest_GetAuthToken {

    @Test
    fun registerUser() {
        var fm = FirebaseManager()

        // SignOut
        var returnParameter1 =  fm.signOut()
        Assert.assertEquals("", fm.getToken())
        Assert.assertEquals(FirebaseReturnOptions.SINGED_OUT, returnParameter1)

        // SignIn User
        var email = "test@student.kit.edu"
        var password = "PSEistsuper"
        val returnParameter2 = fm.signInWithEmailAndPassword(email, password)
        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, returnParameter2)
        Assert.assertNotEquals("", fm.getToken())

        // SignOut Logged in User
        var returnParameter3 =  fm.signOut()
        Assert.assertEquals(FirebaseReturnOptions.SINGED_OUT, returnParameter3)
        Assert.assertEquals("", fm.getToken())
    }
}