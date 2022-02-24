package com.pseandroid2.dailydata.globaltests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.pseandroid2.dailydata.MainActivity
import org.junit.Rule
import org.junit.Test

class GT7114 {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun changeProjectLayout() {

        //create Project
        //open project
        //go to settings
        //add column
        //click save

        //create Project with maxium columns
        //open project
        //go to settings
        //add column
        //get error

    }
}