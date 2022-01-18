package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.flows

import com.pseandroid2.dailydata.DailyDataApp
import com.pseandroid2.dailydata.model.database.daos.ProjectDataDAO
import com.pseandroid2.dailydata.model.database.entities.ProjectData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import javax.inject.Singleton

class ProjectPreviewFlowTest : TestCase() {

    @Before
    public override fun setUp() {
        super.setUp()
    }
    @After
    public override fun tearDown() {}

    @ExperimentalCoroutinesApi
    @Test
    fun testGetProjectPreviewFlow() = runTest(){
        val projectPreviewFlow = ProjectPreviewFlow()
        val flow = projectPreviewFlow.getProjectPreviewFlow()
        launch {
            flow.collect{ it ->}
        }
    }

    @Module
    @InstallIn(DailyDataApp::class)
    object AppDataBaseTestModule {
        @Singleton
        @Provides
        fun bindAnalyticsService(): ProjectDataDAO {
            val projectDataDAO = mockk<ProjectDataDAO>()
            val flow = MutableSharedFlow<List<ProjectData>>()

            launch {
                var i: Int = 0
                val list = ArrayList<ProjectData>()
                while (true) {
                    i++
                    val projectData: ProjectData =
                        ProjectData(i, "testName", "testDescription", 0, "testWallpaper")
                    delay(10000)
                    list.add(projectData)
                    flow.emit(list)
                }
            }

            every { projectDataDAO.getAllProjectData() } returns flow

            return projectDataDAO
        }
    }

}