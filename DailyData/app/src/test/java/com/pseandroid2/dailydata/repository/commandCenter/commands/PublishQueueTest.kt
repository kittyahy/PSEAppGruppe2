package com.pseandroid2.dailydata.repository.commandCenter.commands

import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.test.runTest
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
        val expected = "Rindfleischettikettierungsüberwachungsaufgabenübertragungsgesetz"
        val myCommandList = ArrayList<QueueTestCommand>()
        for (i in 1..expected.length) {
            myCommandList.add(QueueTestCommand(expected.substring(i-1,i)))
        }
        val remoteDataSourceAPI = mockk<RemoteDataSourceAPI>()
        every {remoteDataSourceAPI.connectionToServerPossible()} returns true
        publishQueue = PublishQueue(remoteDataSourceAPI)
        for(myCommand in myCommandList) {
            publishQueue.add(myCommand)
        }
        val oldContent = QueueTestCommand.content
        while(QueueTestCommand.content != expected) {
        }
        assertEquals(expected, QueueTestCommand.content)
        println(QueueTestCommand.content)
    }
}
class QueueTestCommand(private val concat:String) : ProjectCommand(3) {
    companion object {var content: String = ""}
    override fun individualExecution() {
    }

    override fun publish() {
        content += concat
    }
}