package com.pseandroid2.dailydata.globaltests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.pseandroid2.dailydata.MainActivity
import org.junit.Rule
import org.junit.Test

class GT7142 {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun changeDataTypeInFirstColumn() {

        //create project (min 1 graph)
        //input data
        //go to graphs
        //check if change was under 1/4 second

    }
}