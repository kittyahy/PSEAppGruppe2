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

package com.pseandroid2.dailydata.repository.commandCenter.commands

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
/*
class CreateCommandTester {
    private var testOnlineID: Long = 0
    private lateinit var db: AppDataBase
    private lateinit var remoteDataSourceAPI: RemoteDataSourceAPI

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
        remoteDataSourceAPI = mockk<RemoteDataSourceAPI>()
        every { remoteDataSourceAPI.createNewOnlineProject("") } returns testOnlineID
    }/*
    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @Test
    fun integrationTest() = runTest{
        val repository = RepositoryViewModelAPI(db, remoteDataSourceAPI)
        val ph = repository.projectHandler
        val flow = ph.projectPreviewFlow.getFlow()
        val project1 = async { flow.first() }
        val deferred: Deferred<Int> = ph.newProjectAsync(
            "user",
            "description",
            0,
            listOf(Column(0, "column", "unit", DataType.TIME)),
            listOf(Button(0, "button", 0, 0)),
            ArrayList<Notification>(),
            ArrayList<Graph>()
        )
        val id = deferred.await()
        val project = project1.await()
        assertEquals(project[0], ph.getProjectByID(id))
    }*/

}
*/
