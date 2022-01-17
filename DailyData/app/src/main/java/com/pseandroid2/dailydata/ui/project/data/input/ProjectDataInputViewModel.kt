package com.pseandroid2.dailydata.ui.project.data.input

import androidx.lifecycle.ViewModel
import com.pseandroid2.dailydata.di.Repository
import com.pseandroid2.dailydata.util.ui.Row
import com.pseandroid2.dailydata.util.ui.TableButton
import com.pseandroid2.dailydata.util.ui.TableColumn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProjectDataInputScreenViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    var title = ""
    var description = ""
    var buttons : List<TableButton> = listOf()
    var columns : List<TableColumn> = listOf()
    var table : List<Row> = listOf()
}