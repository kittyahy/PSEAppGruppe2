package com.pseandroid2.dailydata.remoteDataSource.userManager.firebaseManager

import android.util.Log
import org.junit.Assert
import org.junit.Test
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions

class FirebaseManagerTest_SignInWhileUserIsAlreadySignedIn_SignInWithEmailAndPassword {

    @Test
    fun signInWhileUserIsAlreadySignedIn() {
        var email1 = "test@student.kit.edu"
        var password1 = "PSEistsuper"

        var email2 = "pseFan@student.kit.edu"
        var password2 = "mehrSpaßAlsBeiPSEGibtsNicht"

        var fm = FirebaseManager()

        // Erster SignIn erfolgt
        val returnParameter1 = fm.signInWithEmailAndPassword(email1, password1)
        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, returnParameter1)
        Assert.assertNotEquals(null, fm.getUser())
        Assert.assertEquals("4hpJh32YaAWrAYoVvo047q7Ey183", fm.getUser()!!.uid)

        // Zweiter SignIn soll erfolgen
        val returnParameter2 = fm.signInWithEmailAndPassword(email2, password2)
        //Assert.assertEquals(FirebaseReturnOptions.SIGN_IN_FAILED, returnParameter2)
        //Assert.assertNotEquals(null, fm.getUser())
        Assert.assertEquals("a5sYrdnd1EX2TtkJAEGGMHqebUq2", fm.getUser()!!.uid)

        fm.signOut()
        Assert.assertEquals(null, fm.getUser())
    }
}