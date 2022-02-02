package com.pseandroid2.dailydata.repository.flows

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.project.SimpleProjectBuilder
import com.pseandroid2.dailydata.model.users.SimpleUser
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectFlowHandler
import com.pseandroid2.dailydata.util.Consts.LOG_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class FlowTester {
    private lateinit var handle: ProjectFlowHandler

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
        handle = ProjectFlowHandler(0, db)
        Log.d(LOG_TAG, "Handle Created")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testProjectFlowHandle() = runTest {
        launch(Dispatchers.IO) {
            Log.d(LOG_TAG, "Initializing Handle")
            handle.initialize()
        }
        launch(Dispatchers.IO) {
            Log.d(LOG_TAG, "Starting Observing Stuff now")
            assertEquals("0", handle.getProject().first {
                if (it != null) {
                    Log.d(
                        LOG_TAG,
                        "${it.name}|${
                            if (it.settings.containsKey("Test")) {
                                it.settings["Test"]
                            } else {
                                ""
                            }
                        }"
                    )
                }
                it != null && it.name != ""
            }!!.name)
            Log.d(LOG_TAG, "Done Asserting")
        }
        launch(Dispatchers.IO) {
            Log.d(LOG_TAG, "Starting Database Stuff now")
            db.projectCDManager()
                .insertProject(
                    SimpleProjectBuilder()
                        .setId(0)
                        .setName("0")
                        .setOnlineProperties(SimpleUser("Test", "Test"), false)
                        .build()
                )
            Log.d(LOG_TAG, "Created Project")
            db.settingsDAO().createProjectSetting(0, "Test", "")
            Log.d(LOG_TAG, "Created Project Setting")
            for (i in 0..100) {
                db.projectDataDAO().setName(0, "$i")
                db.settingsDAO().changeProjectSetting(0, "Test", "$i")
                Log.d(LOG_TAG, "Changed Name and setting")
            }
        }
    }
}