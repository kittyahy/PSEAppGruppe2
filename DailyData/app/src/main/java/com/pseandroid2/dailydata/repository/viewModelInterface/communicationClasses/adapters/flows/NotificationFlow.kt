package com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.adapters.flows

import com.pseandroid2.dailydata.model.database.entities.NotificationEntity
import com.pseandroid2.dailydata.model.notifications.Notification
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@InternalCoroutinesApi
class NotificationFlow(flow: Flow<List<NotificationEntity>>) :
    FlowAdapter<NotificationEntity, com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.Notification>(
        flow
    ) {
    override fun provide(i: NotificationEntity): com.pseandroid2.dailydata.repository.viewModelInterface.communicationClasses.Notification {
        TODO("Not yet implemented")
    }
}