package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.model.database.entities.ProjectData
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows.ProjectPreviewFlow
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.flow.collect
import org.junit.After
import org.junit.Before
import org.junit.Test

class PublishQueueTest : TestCase() {
    lateinit var publishQueue: PublishQueue
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
        val myCommand1 = QueueTestCommand()
        val remoteDataSourceAPI = mockk<RemoteDataSourceAPI>()
        every {remoteDataSourceAPI.connectionToServerPossible()} returns true
        publishQueue = PublishQueue(remoteDataSourceAPI)
        publishQueue.add(myCommand1)
        val oldContent = myCommand1.content
        while(myCommand1.content == 0) {
        }
        assertEquals(1, myCommand1.content)
    }
}
class QueueTestCommand() : ProjectCommand(3) {
    var content: Int = 0
    override fun individualExecution() {
        TODO("Not yet implemented")
    }

    override fun publish() {
        content += 1
    }
}