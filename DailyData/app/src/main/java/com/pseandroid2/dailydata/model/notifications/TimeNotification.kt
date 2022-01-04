package com.pseandroid2.dailydata.model.notifications

import com.pseandroid2.dailydata.model.database.entities.NotificationEntity
import java.time.LocalDate
import java.time.LocalTime

class TimeNotification(
    private val messageString: String,
    private val send: LocalTime,
    initId: Int
) : Notification {
    private var lastSent: LocalDate = LocalDate.now()
    override val id: Int = initId

    companion object {
        fun fromString(paramString: String, message: String, id: Int): TimeNotification {
            val params = paramString.split(",")
            val lastSent: LocalDate = LocalDate.parse(params[0])
            val send: LocalTime = LocalTime.parse(params[1])
            val notification = TimeNotification(message, send, id)
            notification.setSent(lastSent)
            return notification
        }
    }

    override fun getMessage(): String {
        return messageString
    }

    override fun sendNow(vararg args: Any): Boolean {
        if (args.isNotEmpty()) {
            throw IllegalArgumentException()
        }

        if (LocalDate.now() <= lastSent) {
            return false
        }
        return send <= LocalTime.now()
    }

    override fun toFactoryString(): String {
        return "TIME|$lastSent,$send"
    }

    fun setSent() {
        lastSent = LocalDate.now()
    }

    fun setSent(date: LocalDate) {
        lastSent = date
    }
}