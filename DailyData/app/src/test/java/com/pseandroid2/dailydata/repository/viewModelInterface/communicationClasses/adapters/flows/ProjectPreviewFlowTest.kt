package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.database.entities.ProjectData
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.flow.collect
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
        val flow = projectPreviewFlow.getFlow()
        val emissionsCount = 5
        launch {
            flow.collect {
                assertEquals(it.last().id, it.size.toLong())
                if (it.size >= emissionsCount) {
                    this.cancel()
                }
            }
        }
        launch {
            for(i in 1..emissionsCount){
                list.add(ProjectData(i, "TestName", "TestDescription", i.toLong(), "TestWallpaper" ))
                projectDataFlow.emit(list)
            }
        }
    }
}