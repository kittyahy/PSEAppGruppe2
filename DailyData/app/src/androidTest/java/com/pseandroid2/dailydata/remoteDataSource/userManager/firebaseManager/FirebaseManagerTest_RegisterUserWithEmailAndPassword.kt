package com.pseandroid2.dailydata.remoteDataSource.userManager.firebaseManager

import android.util.Log
import org.junit.Assert
import org.junit.Test
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions


class FirebaseManagerTest_RegisterUserWithEmailAndPassword {

    // NOTIZ: Der Test funktioniert, jedoch wurde er entfernt, da man immer eine neue email und password benötigt wird. (Und wir nicht unsere Firebase-Accounts zumüllen wollen)
    @Test
    fun registerUser() {
        var email = "pseFan@student.kit.edu"
        var password = "mehrSpaßAlsBeiPSEGibtsNicht"

        var fm = FirebaseManager()

        fm.signOut()

        val returnParameter = fm.registerUserWithEmailAndPassword(email, password)

        //Assert.assertEquals(FirebaseReturnOptions.REGISTERED, returnParameter)
        //Assert.assertNotEquals(null, fm.getUser())
    }
}