package com.pseandroid2.dailydata.globaltests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.pseandroid2.dailydata.MainActivity
import org.junit.Rule
import org.junit.Test

class GT7116 {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun changeDataTypeInFirstColumn() {

        //create Project with whole number as first column
        //open project
        //click on last row
        //click on delete
        //click save
    }
}