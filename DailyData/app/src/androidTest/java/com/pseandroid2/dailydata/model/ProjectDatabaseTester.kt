package com.pseandroid2.dailydata.model

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.database.daos.ProjectDataDAO
import com.pseandroid2.dailydata.model.database.entities.ProjectEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectSkeletonEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

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
                1,
                ProjectSkeletonEntity("Test", "", "", ""),
                SimpleUser("", ""),
                1
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
            Log.d("XXX", "Start Async")
            val ret = projectDAO.getAllProjectData().first {
                return@first it.size == noProjects && it[noProjects - 1].id == (noProjects - 1)
            }
            Log.d("XXX", "End Async")
            return@async ret
        }
        Log.d("XXX", "Continue Main")

        val user = SimpleUser("", "")

        for (i in 0 until noProjects) {
            projectDAO.insertProjectEntity(
                ProjectEntity(
                    i,
                    ProjectSkeletonEntity("Test$i", "", "", ""),
                    user,
                    i.toLong()
                )
            )
            Log.d("XXX", "Inserted $i")
        }

        val deferredList = list.await()

        assertEquals(noProjects, deferredList.size)
        for (i in 0 until noProjects) {
            assertEquals(i, deferredList[i].id)
            assertEquals("Test$i", deferredList[i].name)
            assertEquals(i.toLong(), deferredList[i].onlineId)
        }
        Log.d("XXX", "Completed Test")
    }
}

