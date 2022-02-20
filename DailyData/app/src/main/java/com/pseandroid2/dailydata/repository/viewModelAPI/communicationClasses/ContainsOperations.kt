package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

interface ContainsOperations {
    val isIllegalOperation: Map<String, Flow<Boolean>>
        @Suppress("DEPRECATION")
        get() = mutableIllegalOperation

    @Deprecated("Internal function, should not be used outside the RepositoryViewModelAPI")
    val mutableIllegalOperation: MutableMap<String, MutableSharedFlow<Boolean>>

    fun addFlows(vararg containsOperations: ContainsOperations) {
        for (entry in containsOperations) {
            @Suppress("DEPRECATION")
            mutableIllegalOperation.putAll(entry.mutableIllegalOperation)
        }
    }
}