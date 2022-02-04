package com.pseandroid2.dailydata.repository.flows

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.project.SimpleProjectBuilder
import com.pseandroid2.dailydata.model.users.SimpleUser
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectFlowProvider
import com.pseandroid2.dailydata.util.Consts.LOG_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class FlowTester {
    private lateinit var handle: ProjectFlowProvider
    var id = 0

    companion object {
        private lateinit var context: Context
        private lateinit var db: AppDataBase

        @JvmStatic
        @BeforeClass
        fun classSetup() {
            context = ApplicationProvider.getApplicationContext()
            db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
        }
    }

    @Before
    fun setup() {
        runBlocking {
            id = db.projectCDManager()
                .insertProject(
                    SimpleProjectBuilder()
                        .setId(0)
                        .setName("0")
                        .setOnlineProperties(SimpleUser("Test", "Test"), false)
                        .build()
                ).id
        }
        Log.d(LOG_TAG, "Created Project $id")
        handle = ProjectFlowProvider(id, db)
        Log.d(LOG_TAG, "Handle Created")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testProjectFlowHandle() = runTest {
        val job = launch(Dispatchers.IO) {
            Log.d(LOG_TAG, "Initializing Handle")
            handle.initialize()
        }
        launch(Dispatchers.IO) {
            Log.d(LOG_TAG, "Starting Observing Stuff now")
            val proj = handle.provideFlow.first {
                it != null && it.name == "50"
            }!!
            Log.d(LOG_TAG, "Done Asserting")
            job.cancel()
        }
        launch(Dispatchers.IO) {
            Log.d(LOG_TAG, "Starting Database Stuff now")
            db.settingsDAO().createProjectSetting(id, "Test", "")
            Log.d(LOG_TAG, "Created Project Setting")
            for (i in 0..100) {
                db.projectDataDAO().setName(id, "${i / 2}")
                db.settingsDAO().changeProjectSetting(id, "Test", "$i")
                Log.d(LOG_TAG, "Changed Name and setting")
            }
        }
    }
}