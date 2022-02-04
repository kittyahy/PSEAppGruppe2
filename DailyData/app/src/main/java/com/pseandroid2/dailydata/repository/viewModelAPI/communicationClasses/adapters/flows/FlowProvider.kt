package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class FlowProvider<T : Any?> {
    protected val mutableFlow = MutableSharedFlow<T>()
    val provideFlow = mutableFlow.asSharedFlow()

    abstract suspend fun initialize()
}