package com.pseandroid2.dailydata.ui.project.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pseandroid2.dailydata.di.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProjectDataScreenViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var tab by mutableStateOf(0)
        private set

    fun onTabClick(newTab : Int) {
        tab = newTab
    }

}