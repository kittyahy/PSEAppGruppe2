package com.pseandroid2.dailydata

import android.util.Log
import org.junit.Test
import org.junit.AfterClass
import org.junit.Before

// This class is done as a reference on how to implement @AfterClass Methods in kotlin
class AfterClassTest {

    private var test: String = "Complete"

    @Before
    fun setup() {
        // Set Values in companion object for correct teardown
        setTest(test)
    }

    companion object Teardown{
        private var test: String = ""

        fun setTest(newTest: String) {
            test = newTest
        }

        @AfterClass @JvmStatic fun teardown() {
            Log.d("Teardown", test)
        }
    }

    @Test
    fun test() {

    }
}