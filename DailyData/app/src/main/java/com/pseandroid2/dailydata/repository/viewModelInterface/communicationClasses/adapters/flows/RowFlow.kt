package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.table.Row
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@InternalCoroutinesApi
class RowFlow(flow: Flow<List<Row>>) :
    FlowAdapter<Row, com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.Row>(
        flow
    ) {
    override fun provide(i: Row): com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.Row {
        TODO("Not yet implemented")
    }
}