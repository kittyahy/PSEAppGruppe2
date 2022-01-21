package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.Button
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@InternalCoroutinesApi
class ButtonFlow(flow: Flow<List<UIElement>>) : FlowAdapter<UIElement, Button>(flow) {
    override fun provide(i: UIElement): Button {
        TODO("Not yet implemented")
    }
}