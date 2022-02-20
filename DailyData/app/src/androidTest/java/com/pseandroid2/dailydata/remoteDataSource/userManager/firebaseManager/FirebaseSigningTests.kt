/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/

package com.pseandroid2.dailydata.remoteDataSource.userManager.firebaseManager

import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseManager
import com.pseandroid2.dailydata.remoteDataSource.userManager.FirebaseReturnOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FirebaseSigningTests {
    private var fm = FirebaseManager(null)
    private var email = "test@student.kit.edu"

    private var password = "PSEistsuper"
    private var wrongPassword = "WrongPassword"

    private var email2 = "pseFan@student.kit.edu"
    private var password2 = "mehrSpaßAlsBeiPSEGibtsNicht"

    @Before
    fun setup() = runBlocking {
        Assert.assertEquals(FirebaseReturnOptions.SINGED_OUT, fm.signOut())
        Assert.assertEquals("", fm.getUserID())
    }

    @ExperimentalCoroutinesApi
    @After
    fun cleanUp() = runTest {
        fm.signOut()
        Assert.assertEquals("", fm.getUserID())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun signInWithEmail() = runTest {
        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email, password))
        Assert.assertEquals("4hpJh32YaAWrAYoVvo047q7Ey183", fm.getUserID())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun signInWithEmailWithWrongPassword() = runTest {
        Assert.assertEquals(FirebaseReturnOptions.SIGN_IN_FAILED, fm.signInWithEmailAndPassword(email, wrongPassword))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun signInNewUserWithEmailWithAlreadySignedInUser() = runTest {
        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email, password))
        Assert.assertEquals("4hpJh32YaAWrAYoVvo047q7Ey183", fm.getUserID())

        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email2, password2))
        Assert.assertEquals("a5sYrdnd1EX2TtkJAEGGMHqebUq2", fm.getUserID())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun signOut() = runTest {
        Assert.assertEquals("", fm.getUserID())

        // SignIn User
        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email, password))
        Assert.assertNotEquals("", fm.getUserID())

        // SignOut Logged in User
        Assert.assertEquals(FirebaseReturnOptions.SINGED_OUT, fm.signOut())
        Assert.assertEquals("", fm.getUserID())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun alreadyRegisteredWithThisEmailAndPassword() = runTest {
        val returnParameter = fm.registerUserWithEmailAndPassword(email, password)
        Assert.assertEquals(FirebaseReturnOptions.REGISTRATION_FAILED, returnParameter)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun wrongInputParameters() = runTest {
        Assert.assertEquals(FirebaseReturnOptions.WRONG_INPUT_PARAMETERS, fm.signInWithEmailAndPassword(email, ""))
        Assert.assertEquals(FirebaseReturnOptions.WRONG_INPUT_PARAMETERS, fm.signInWithEmailAndPassword("", password))
        Assert.assertEquals(FirebaseReturnOptions.WRONG_INPUT_PARAMETERS, fm.signInWithEmailAndPassword("", ""))

        Assert.assertEquals(FirebaseReturnOptions.WRONG_INPUT_PARAMETERS, fm.registerUserWithEmailAndPassword(email, ""))
        Assert.assertEquals(FirebaseReturnOptions.WRONG_INPUT_PARAMETERS, fm.registerUserWithEmailAndPassword("", ""))
        Assert.assertEquals(FirebaseReturnOptions.WRONG_INPUT_PARAMETERS, fm.registerUserWithEmailAndPassword("", password))
    }
}