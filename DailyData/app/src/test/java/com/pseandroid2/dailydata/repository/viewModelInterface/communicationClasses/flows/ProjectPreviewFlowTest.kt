package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.flows

import com.pseandroid2.dailydata.model.database.entities.ProjectData
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.ProjectPreview
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class ProjectPreviewFlowTest : TestCase() {

    @Before
    public override fun setUp() {
        super.setUp()
    }
    @After
    public override fun tearDown() {}


    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @Test
    fun testGetProjectPreviewFlow() = runTest(){
        val list = ArrayList<ProjectData>()
        val projectDataFlow = MutableSharedFlow<List<ProjectData>>()
        val projectPreviewFlow = ProjectPreviewFlow(projectDataFlow)
        val flow = projectPreviewFlow.getProjectPreviewFlow()
        launch {
            flow.collect{ it -> println(it.size)}
        }
        launch {
            for(i in 1..5){
                list.add(ProjectData(i, "TestName", "TestDescription", i.toLong(), "TestWallpaper" ))
                projectDataFlow.emit(list)
            }
        }
    }

    /*@Module
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
    }*/

}