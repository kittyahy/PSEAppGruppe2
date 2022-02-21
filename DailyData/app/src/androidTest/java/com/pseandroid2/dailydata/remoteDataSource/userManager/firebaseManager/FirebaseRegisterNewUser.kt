package com.pseandroid2.dailydata.remoteDataSource.userManager.firebaseManager

import org.junit.Assert
import org.junit.Test
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import com.pseandroid2.dailydata.remoteDataSource.userManager.TestsUsers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest


class FirebaseRegisterNewUser {
     // NOTE: The test works but it got removed because every time a new email and password ist needed and we don't want to have to many test firebase accounts in our firebase-project

    @ExperimentalCoroutinesApi
    @Test
    fun registerNewUser() = runTest {
        var email = "pseFan@student.kit.edu"
        var password = "mehrSpaßAlsBeiPSEGibtsNicht"

        var fm = FirebaseManager(null)


         for (i in 3..TestsUsers.email.size-1) {
             Assert.assertEquals(FirebaseReturnOptions.REGISTERED, fm.registerUserWithEmailAndPassword(TestsUsers.email[i], TestsUsers.password[i]))
         }
    }

}