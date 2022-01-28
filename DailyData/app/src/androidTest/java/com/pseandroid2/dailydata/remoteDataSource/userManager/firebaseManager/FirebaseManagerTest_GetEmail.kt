package com.pseandroid2.dailydata.remoteDataSource.userManager.firebaseManager

import android.util.Log
import org.junit.Assert
import org.junit.Test
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions

class FirebaseManagerTest_GetEmail {

    @Test
    fun getEmail() {
        var email = "test@student.kit.edu"
        var password = "PSEistsuper"

        var fm = FirebaseManager(null)

        fm.signOut()
        Assert.assertEquals("", fm.getUserEMail())

        val returnParameter = fm.signInWithEmailAndPassword(email, password)

        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, returnParameter)
        Assert.assertNotEquals("", fm.getUserID())
        Assert.assertEquals("4hpJh32YaAWrAYoVvo047q7Ey183", fm.getUserID())
        Assert.assertEquals("test@student.kit.edu", fm.getUserEMail())

        fm.signOut()
        Assert.assertEquals("", fm.getUserID())
        Assert.assertEquals("", fm.getUserEMail())
    }
}