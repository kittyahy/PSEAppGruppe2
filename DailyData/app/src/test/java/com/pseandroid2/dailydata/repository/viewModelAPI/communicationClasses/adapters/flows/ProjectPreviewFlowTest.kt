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

package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows
/*
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
}*/ //TODO wieder rein oder ganz raus