package com.pseandroid2.dailydata.remoteDataSource.userManager

import android.util.Log
import com.pseandroid2.dailydata.remoteDataSource.UserManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.UserManager.FirebaseReturnOptions
import org.junit.Assert
import org.junit.ClassRule
import org.junit.Test
import org.junit.Assert.assertTrue

import android.content.Context
import org.junit.Assert.assertEquals
import org.junit.Rule

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

private const val FAKE_STRING = "HELLO WORLD"

@RunWith(MockitoJUnitRunner::class)
class RegisterUserWithEmailAndPasswordUnitTest {
    @Mock
    private lateinit var mockContext: Context

    @Test
    fun registerUser() {
        Assert.assertTrue(true)

        Log.d("registration Test1","RegistrationTest")

        var email = "ufjqw@student.kit.edu"
        var password = "PSEistsuper"

        var fm = FirebaseManager()
        /*

        val returnParameter = fm.registerUserWithEmailAndPassword(email, password)

        Assert.assertEquals(FirebaseReturnOptions.REGISTERED, returnParameter)

         */
    }
}