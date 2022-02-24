package com.pseandroid2.dailydata.globaltests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.pseandroid2.dailydata.MainActivity
import org.junit.Rule
import org.junit.Test

class GT7124 {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun changeDataTypeInFirstColumn() {

        //create project
        //go to project
        //go to graphs
        //click on graph
        //click on Apply Template
        //click on template (does nothing)
        //click on ok
        //check if each field has value (does not)

    }
}