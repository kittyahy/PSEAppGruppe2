package com.pseandroid2.dailydata.model.notifications

interface Notification {

    val id: Int

    /**
     * @return the Message that should be delivered by this Notification
     */
    fun getMessage(): String

    fun sendNow(vararg args: Any): Boolean

    /**
     * @return a String from which to recreate this Notification.
     * Has to start with an identifier followed by a '|'.
     */
    fun toFactoryString(): String
    
    companion object {
        fun fromString(notificationString: String, message: String, id: Int): Notification {
            return when (notificationString.split("|")[0]) {
                "TIME" -> TimeNotification.fromString(notificationString, message, id)
                else -> {
                    throw IllegalArgumentException()
                }
            }
        }
    }

}