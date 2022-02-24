package com.pseandroid2.dailydata.globaltests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.pseandroid2.dailydata.MainActivity
import org.junit.Rule
import org.junit.Test

class GT7128 {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun changeDataTypeInFirstColumn() {

        //create project with pie chart
        //go to project
        //go to graphs
        //click on graph
        //change each value and see if something changes

    }
}