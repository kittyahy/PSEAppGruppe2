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

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CreatePersistentProjectTest {

    @ExperimentalCoroutinesApi
    @Test
    fun execute() = runTest {/*
        stringTest("Donaudampfschiffahrtskapitänsmütze", 124)*/
    }

    @ExperimentalCoroutinesApi
    private fun stringTest(testString: String, testOnlineID: Long) = runTest {
        /*val slot = slot<Project>()
        val idFlow = MutableSharedFlow<Int>()
        val createProject =
            CreateProject(
                "user",
                testString,
                "description",
                0,
                listOf(Column(0, "column", "unit", DataType.TIME)),
                listOf(Button(0, "button", 0, 0)),
                ArrayList<Notification>(),
                ArrayList<Graph>(),
                idFlow
            )
        val projectDataDAO = mockk<ProjectDataDAO>()
        coEvery { projectDataDAO.setOnlineID(any<Int>(), any<Long>()) } returns Unit
        val simpleProject = mockk<SimpleProject>()
        every { simpleProject.id } returns 1
        val appDataBase = mockk<AppDataBase>()
        coEvery {
            appDataBase.projectCDManager().insertProject(capture(slot))
        } returns simpleProject
        every {
            appDataBase.projectDataDAO()
        } returns projectDataDAO
        val remoteDataSourceAPI = mockk<RemoteDataSourceAPI>()
        every { remoteDataSourceAPI.createNewOnlineProject("") } returns testOnlineID // TODO add project details
        val publishQueue : PublishQueue= mockk()
        coEvery { publishQueue.add(any<ProjectCommand>()) } returns Unit
        val task = async {
            createProject.execute(
                appDataBase,
                remoteDataSourceAPI,
                publishQueue,
            )
        }
        task.await()
        assertEquals(testOnlineID, createProject.onlineProjectID)
        assertEquals(testString, slot.captured.name)
        */
    }
}