package com.pseandroid2.dailydata.model.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.pseandroid2.dailydata.model.notifications.Notification
import com.pseandroid2.dailydata.model.database.entities.NotificationEntity
import com.pseandroid2.dailydata.util.SortedIntListUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import kotlin.collections.ArrayList

@Dao
abstract class NotificationsDAO {
    private val existingIds: MutableMap<Int, out SortedSet<Int>> = mutableMapOf<Int, TreeSet<Int>>()

    fun getNotifications(projectId: Int): Flow<List<Notification>> {
        return getNotificationEntities(projectId).map {
            val list: MutableList<Notification> = ArrayList()
            for (entity: NotificationEntity in it) {
                list.add(Notification.fromString(entity.params, entity.message, entity.id))
            }
            list.toList()
        }
    }

    fun insertNotification(projectId: Int, notification: Notification): Int {
        val id = getNextId(projectId)
        insertNotificationEntity(
            NotificationEntity(
                projectId,
                id,
                notification.toFactoryString(),
                notification.getMessage()
            )
        )
        return id
    }

    fun deleteNotification(projectId: Int, notification: Notification) {

    }

    @Query(
        "UPDATE notification SET message = :message " +
                "WHERE projectId = :projectId AND id = :id"
    )
    abstract fun setNotificationMessage(projectId: Int, id: Integer, message: String)

    /*========================SHOULD ONLY BE CALLED FROM INSIDE THE MODEL=========================*/
    @Query("SELECT * FROM notification WHERE projectId = :projectId")
    abstract fun getNotificationEntities(projectId: Int): Flow<List<NotificationEntity>>

    @Insert
    abstract fun insertNotificationEntity(notificationEntity: NotificationEntity)

    @Delete
    abstract fun deleteNotificationEntity(notificationEntity: NotificationEntity)

    private fun getNextId(projectId: Int): Int {
        //Get the List of existing Ids for the project
        val list: List<Int> = ArrayList(existingIds[projectId] ?: sortedSetOf())

        //Get the next missing id
        return SortedIntListUtil.getFirstMissingInt(list)
    }
}