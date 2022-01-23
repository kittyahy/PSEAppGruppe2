package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses.Member
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@InternalCoroutinesApi
//TODO Nothing durch richtigen Typ ersetzen
class MemberFlow(flow: Flow<List<Nothing>>) : FlowAdapter<Nothing, Member>(flow) {
    override fun provide(i: Nothing): Member {
        TODO("Not yet implemented")
    }
}