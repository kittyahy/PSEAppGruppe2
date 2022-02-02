package com.pseandroid2.dailydata.repository.flows

import android.content.Context
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectFlowHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
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
            context = 
        }
    }

    @Before
    fun setup() {

        handle = ProjectFlowHandler(0)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testProjectFlowHandle() = runTest {
        launch(Dispatchers.IO) {

        }
    }
}