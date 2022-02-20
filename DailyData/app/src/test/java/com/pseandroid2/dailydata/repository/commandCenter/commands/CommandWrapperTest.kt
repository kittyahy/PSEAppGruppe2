package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.repository.RepositoryViewModelAPI
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CommandWrapperTest : TestCase() {


    @ExperimentalCoroutinesApi
    @Test
    fun testCommandWrapper() = runTest(){
        /*
        val id: Int = 42
        val testCommand = TestCommand(id)
        val json = CommandWrapper(testCommand).toJson()
        val command : ProjectCommand = CommandWrapper.fromJson(json)
        command.execute(mockk<AppDataBase>(), mockk<RemoteDataSourceAPI>(), mockk<PublishQueue>(),)
        assertEquals(id * 2, command.projectID)*/
    }
}

class TestCommand(projectID: Int) : ProjectCommand( projectID) {

    override suspend fun execute(
        repositoryViewModelAPI: RepositoryViewModelAPI,
        publishQueue: PublishQueue
    ) {
        projectID = projectID?.times(2)
    }
}