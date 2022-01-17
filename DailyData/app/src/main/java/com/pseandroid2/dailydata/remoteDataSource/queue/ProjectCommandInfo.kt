package com.pseandroid2.dailydata.remoteDataSource.queue
import java.time.LocalDateTime

/**
 * @param wentOnline: Wann der Command hochgeladen wurde
 * @param serverRemoveTime: Wie lange Commands auf dem Server bleiben dürfen (Ab dem Zeitpunkt, wo die hochgeladen wurden)
 * @param commandByUser: Wer den Command hochgeladen hat
 * @param isProjectAdmin: Ob der Nutzender, der den Command hochgeladen hat, zu dem Zeitpunkt ein Projekt-Admin war
 * @param projectCommand: Der ProjectCommand als JSON
 */
data class ProjectCommandInfo(val wentOnline: LocalDateTime = java.time.LocalDateTime.parse("0001-01-01T00:00"),
                              val serverRemoveTime: LocalDateTime = java.time.LocalDateTime.parse("0001-01-01T00:00"),
                              val commandByUser: String = "",
                              val isProjectAdmin: Boolean = false,
                              val projectCommand: String = "")