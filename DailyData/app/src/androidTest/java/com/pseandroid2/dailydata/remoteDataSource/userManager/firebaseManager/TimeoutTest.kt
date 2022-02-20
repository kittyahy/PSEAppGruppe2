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
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TimeoutTest {
    private val fm = FirebaseManager(0)
    private var email = "test@student.kit.edu"
    private var password = "PSEistsuper"

    @ExperimentalCoroutinesApi
    @Test
    fun registerTimeout() = runTest {
        Assert.assertEquals(FirebaseReturnOptions.TIMEOUT, fm.registerUserWithEmailAndPassword(email, password))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emailSignInTimeout() = runTest {
        Assert.assertEquals(FirebaseReturnOptions.TIMEOUT, fm.signInWithEmailAndPassword(email, password))
    }
}
