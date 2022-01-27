package com.pseandroid2.dailydata.repository.commandCenter.commands
/*
import com.pseandroid2.dailydata.remoteDataSource.RemoteDataSourceAPI
import com.pseandroid2.dailydata.repository.commandCenter.CommandQueue
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import com.pseandroid2.dailydata.repository.commandCenter.PublishQueue
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class QueueTest : TestCase() {
    lateinit var remoteDataSourceAPI: RemoteDataSourceAPI
    @Before
    public override fun setUp() {
        super.setUp()
        remoteDataSourceAPI = mockk<RemoteDataSourceAPI>()
        every {remoteDataSourceAPI.connectionToServerPossible()} returns true
    }
    @After
    public override fun tearDown() {}


    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @Test
    fun testStringPublishQueue() = runTest(){
        queueStringTest("Rindfleischettikettierungsüberwachungsaufgabenübertragungsgesetz", PublishQueue(remoteDataSourceAPI))
    }
    @Test
    fun testStringExecuteQueue() = runTest() {
        queueStringTest("Ein belegtes Brot mit Schinken", ExecuteQueue(remoteDataSourceAPI))
    }
    private suspend fun queueStringTest(expected: String, publishQueue: CommandQueue) {
        val myCommandList = ArrayList<QueueTestCommand>()
        for (i in 1..expected.length) {
            myCommandList.add(QueueTestCommand(expected.substring(i-1,i)))
        }
        for(myCommand in myCommandList) {
            publishQueue.add(myCommand)
        }
        while(QueueTestCommand.content != expected) {
        }
        assertEquals(expected, QueueTestCommand.content)
        println(QueueTestCommand.content)
        QueueTestCommand.content = ""
    }
}
class QueueTestCommand(private val concat:String) : ProjectCommand(3) {
    companion object {var content: String = ""}

    override suspend fun publish() {
        content += concat
    }
    override suspend fun execute() {
        publish()
    }
}*/ //TODO wieder rein oder ganz raus