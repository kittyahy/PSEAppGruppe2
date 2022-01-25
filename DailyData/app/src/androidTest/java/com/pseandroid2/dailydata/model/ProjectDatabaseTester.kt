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

package com.pseandroid2.dailydata.model

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.database.daos.ProjectDataDAO
import com.pseandroid2.dailydata.model.database.entities.ProjectData
import com.pseandroid2.dailydata.model.database.entities.ProjectEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectSkeletonEntity
import com.pseandroid2.dailydata.model.users.SimpleUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.lang.NullPointerException

class ProjectDatabaseTester {
    private lateinit var db: AppDataBase
    private lateinit var projectDAO: ProjectDataDAO

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
        projectDAO = db.projectDataDAO()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testProjectInsertion() = runTest {

        val list = async { projectDAO.getAllProjectData().first { return@first it.isNotEmpty() } }

        projectDAO.insertProjectEntity(
            ProjectEntity(
                ProjectSkeletonEntity(1, "Test", "", "", "", 1),
                SimpleUser("", "")
            )
        )

        val deferredList = list.await()

        val lastId = deferredList[0].id
        val lastName = deferredList[0].name

        assertEquals(1, lastId)
        assertEquals("Test", lastName)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testMultipleInsertions() = runTest {

        val noProjects = 5

        val list = async {
            val ret = projectDAO.getAllProjectData().first {
                return@first it.size == noProjects && it[noProjects - 1].id == (noProjects - 1)
            }
            return@async ret
        }

        val user = SimpleUser("", "")

        for (i in 0 until noProjects) {
            projectDAO.insertProjectEntity(
                ProjectEntity(
                    ProjectSkeletonEntity(i, "Test$i", "", "", "", i.toLong()),
                    user
                )
            )
        }

        val deferredList = list.await()

        assertEquals(noProjects, deferredList.size)
        for (i in 0 until noProjects) {
            assertEquals(i, deferredList[i].id)
            assertEquals("Test$i", deferredList[i].name)
            assertEquals(i.toLong(), deferredList[i].onlineId)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testNonExistingGet() {
        assertThrows(NullPointerException::class.java) {
            runTest {
                projectDAO.insertProjectEntity(
                    ProjectEntity(
                        ProjectSkeletonEntity(1, "Test", "", "", "", 1),
                        SimpleUser("", "")
                    )
                )

                val retProj: ProjectData = projectDAO.getProjectData(0).first()!!
            }
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun testRemoveProject() = runTest {
        val ent = ProjectEntity(
            ProjectSkeletonEntity(1, "Test", "", "", "", 1),
            SimpleUser("", "")
        )
        projectDAO.insertProjectEntity(ent)
        assertEquals(ProjectData(1, "Test", "", 1, ""), projectDAO.getProjectData(1).first()!!)
        projectDAO.deleteProjectEntity(ent)
        assertNull(projectDAO.getProjectData(1).first())
    }
}

