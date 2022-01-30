package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.project.SimpleProject
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test

class CreateProjectTest {

    @ExperimentalCoroutinesApi
    @Test
    fun execute() = runTest{
        stringTest("Donaudampfschiffahrtskapitänsmütze")

    }
    @ExperimentalCoroutinesApi
    private fun stringTest(testString: String) = runTest {
        val slot = slot<Project>()
        val createProject =
            CreateProject(
                "user",
                testString,
                "description",
                0,
                listOf(Column(0, "column", "unit", DataType.TIME)),
                listOf(Button(0, "button", 0, 0)),
                ArrayList<Notification>(),
                ArrayList<Graph>()
            )
        val appDataBase = mockk<AppDataBase>()
        coEvery {appDataBase.projectCDManager().insertProject(capture(slot))} returns mockk<SimpleProject>()
        val task = async {createProject.execute(
            appDataBase,
            mockk<RemoteDataSourceAPI>(),,
        )}
        task.await()
        assertEquals(testString, slot.captured.name)
    }
}