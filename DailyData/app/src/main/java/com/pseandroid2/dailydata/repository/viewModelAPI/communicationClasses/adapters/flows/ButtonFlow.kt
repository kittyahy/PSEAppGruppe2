package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.database.entities.UIElementMap
import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Button
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@InternalCoroutinesApi
class ButtonFlow(flow: Flow<List<UIElementMap>>) : FlowAdapter<UIElementMap, Button>(flow) {
    override fun provide(i: UIElementMap): Button {
        TODO("Not yet implemented")
    }
}