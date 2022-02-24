package com.pseandroid2.dailydata.globaltests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.pseandroid2.dailydata.MainActivity
import org.junit.Rule
import org.junit.Test

class GT7115 {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun changeDataTypeInFirstColumn() {

        //create Project with whole number as first column
        //open project
        //go to settings
        //delete first column
        //add first column with time
        //click save

        //create Project with time as first column
        //open project
        //go to settings
        //delete first column
        //add first column with whole number
        //click save
    }
}