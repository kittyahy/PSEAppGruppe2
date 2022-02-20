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

class FirebaseUserData {
    private val fm = FirebaseManager(null)
    private var email = "test@student.kit.edu"
    private var password = "PSEistsuper"

    @ExperimentalCoroutinesApi
    @Before
    fun setup() = runBlocking {
        // SignOut
        Assert.assertEquals(FirebaseReturnOptions.SINGED_OUT, fm.signOut())
    }

    @ExperimentalCoroutinesApi
    @After
    fun signOut() = runTest {
        Assert.assertEquals(FirebaseReturnOptions.SINGED_OUT, fm.signOut())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getAuthToken() = runTest {
        // SignIn User
        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email, password))
        Assert.assertNotEquals("", fm.getToken())

        // SignOut Logged in User
        Assert.assertEquals(FirebaseReturnOptions.SINGED_OUT, fm.signOut())
        Assert.assertEquals("", fm.getToken())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getEmail() = runTest {
        Assert.assertEquals("", fm.getUserEMail())

        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email, password))
        Assert.assertEquals("test@student.kit.edu", fm.getUserEMail())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getUserID() = runTest {
        Assert.assertEquals("", fm.getUserID())

        Assert.assertEquals(FirebaseReturnOptions.SINGED_IN, fm.signInWithEmailAndPassword(email, password))
        Assert.assertEquals("4hpJh32YaAWrAYoVvo047q7Ey183", fm.getUserID())
    }
}
