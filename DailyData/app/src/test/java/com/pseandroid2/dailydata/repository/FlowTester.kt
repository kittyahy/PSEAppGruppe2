package com.pseandroid2.dailydata.repository

import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Project
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class FlowTester {

    @Test
    fun testProjectCreation() {
        val proj = Project()
        runBlocking {
            assertTrue(proj.addRowIsPossible()?.first()!!)
        }
    }
}