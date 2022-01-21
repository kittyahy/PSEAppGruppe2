package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.adapters.flows

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@InternalCoroutinesApi
abstract class FlowAdapter <I, O> (val flow: Flow<List<I>>){
    private val sharedFlow = MutableSharedFlow<List<O>>()
    init {
        GlobalScope.launch {
            adapt()
        }
    }

    @JvmName("getFlow1")
    fun getFlow(): Flow<List<O>> {
        return sharedFlow
    }
    @InternalCoroutinesApi
    open suspend fun adapt() {
        flow.collect { list ->
            val listO = ArrayList<O>()
            for (i: I in list) {
                val o = provide(i)
                listO.add(o)
            }
            sharedFlow.emit(listO)
        }
    }
    abstract fun provide(i: I): O
}