package com.pseandroid2.dailydata.repository.commandCenter.commands

import junit.framework.TestCase
import org.junit.Test

class CommandWrapperTest : TestCase() {


    @Test
    fun testCommandWrapper() {
        val id: Int = 42
        val testCommand = TestCommand(id)
        val json = CommandWrapper(testCommand).toJson()
        val command : ProjectCommand = CommandWrapper.fromJson(json)
        command.execute()
        assertEquals(id * 2, command.projectID)
    }
}

class TestCommand(projectID: Int) : ProjectCommand(projectID) {
    override fun individualExecution() {
        projectID *= 2
    }

    override fun execute() {
        individualExecution()
    }
}