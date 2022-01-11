package com.pseandroid2.dailydata.ui.project

import androidx.lifecycle.ViewModel
import com.pseandroid2.dailydata.di.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProjectScreenViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


}