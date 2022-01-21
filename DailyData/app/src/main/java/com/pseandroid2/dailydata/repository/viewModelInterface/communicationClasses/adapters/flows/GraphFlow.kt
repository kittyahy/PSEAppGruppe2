package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.database.entities.GraphEntity
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.Graph
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@InternalCoroutinesApi
class GraphFlow(flow: Flow<List<GraphEntity>>) : FlowAdapter<GraphEntity, Graph>(flow) {
    override fun provide(i: GraphEntity): Graph {
        TODO("Not yet implemented")
    }
}