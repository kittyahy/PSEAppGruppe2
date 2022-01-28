package com.pseandroid2.dailydata.remoteDataSource.userManager.firebaseManager

import org.junit.Assert
import org.junit.Test
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions


class FirebaseRegisterNewUser {

    // NOTE: The test works but it got removed because every time a new email and password ist needed and we don't want to have to many test firebase accounts in our firebase-project
    @Test
    fun registerNewUser() {
        var email = "pseFan@student.kit.edu"
        var password = "mehrSpaßAlsBeiPSEGibtsNicht"

        var fm = FirebaseManager(null)

        fm.signOut()

        //Assert.assertEquals(FirebaseReturnOptions.REGISTERED, fm.registerUserWithEmailAndPassword(email, password))
    }
}