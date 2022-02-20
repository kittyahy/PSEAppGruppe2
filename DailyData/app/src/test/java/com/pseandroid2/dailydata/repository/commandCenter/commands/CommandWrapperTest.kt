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

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CommandWrapperTest : TestCase() {


    @ExperimentalCoroutinesApi
    @Test
    fun testCommandWrapper() = runTest(){
        /*
        val id: Int = 42
        val testCommand = TestCommand(id)
        val json = CommandWrapper(testCommand).toJson()
        val command : ProjectCommand = CommandWrapper.fromJson(json)
        command.execute(mockk<AppDataBase>(), mockk<RemoteDataSourceAPI>(), mockk<PublishQueue>(),)
        assertEquals(id * 2, command.projectID)*/
    }
}

class TestCommand(projectID: Int) : ProjectCommand( projectID) {

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        projectID = projectID?.times(2)
    }
}