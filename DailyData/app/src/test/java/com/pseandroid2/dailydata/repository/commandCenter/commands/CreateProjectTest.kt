package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.database.daos.ProjectDataDAO
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.project.SimpleProject
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Column
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.DataType
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Graph
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Notification
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test

class CreateProjectTest {

    @ExperimentalCoroutinesApi
    @Test
    fun execute() = runTest {
        stringTest("Donaudampfschiffahrtskapitänsmütze", 124)
    }

    @ExperimentalCoroutinesApi
    private fun stringTest(testString: String, testOnlineID: Long) = runTest {
        val slot = slot<Project>()
        val idFlow = MutableSharedFlow<Int>()
        val createProject =
            CreateProject(
                "user",
                testString,
                "description",
                0,
                listOf(Column(0, "column", "unit", DataType.TIME)),
                listOf(Button(0, "button", 0, 0)),
                ArrayList<Notification>(),
                ArrayList<Graph>(),
                idFlow
            )
        val projectDataDAO = mockk<ProjectDataDAO>()
        coEvery { projectDataDAO.setOnlineID(any<Int>(), any<Long>()) } returns Unit
        val simpleProject = mockk<SimpleProject>()
        every { simpleProject.id } returns 1
        val appDataBase = mockk<AppDataBase>()
        coEvery {
            appDataBase.projectCDManager().insertProject(capture(slot))
        } returns simpleProject
        every {
            appDataBase.projectDataDAO()
        } returns projectDataDAO
        val remoteDataSourceAPI = mockk<RemoteDataSourceAPI>()
        every { remoteDataSourceAPI.addProject() } returns testOnlineID
        val publishQueue : PublishQueue= mockk()
        coEvery { publishQueue.add(any<ProjectCommand>()) } returns Unit
        val task = async {
            createProject.execute(
                appDataBase,
                remoteDataSourceAPI,
                publishQueue
            )
        }
        task.await()
        assertEquals(testOnlineID, createProject.onlineProjectID)
        assertEquals(testString, slot.captured.name)
    }
}