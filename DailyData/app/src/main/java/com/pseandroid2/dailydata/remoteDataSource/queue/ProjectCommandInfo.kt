package com.pseandroid2.dailydata.remoteDataSource.queue
import java.time.LocalDateTime

/**
 * @param wentOnline: When the command was uploaded
 * @param serverRemoveTime: How long commands can stay on the server
 * @param commandByUser: Who uploaded the command
 * @param isProjectAdmin: Was the user who created the command an admin?
 * @param projectCommand: The project command as JSON
 */
data class ProjectCommandInfo(val wentOnline: LocalDateTime = java.time.LocalDateTime.parse("0001-01-01T00:00"),
                              val serverRemoveTime: LocalDateTime = java.time.LocalDateTime.parse("0001-01-01T00:00"),
                              val commandByUser: String = "",
                              val isProjectAdmin: Boolean = false,
                              val projectCommand: String = "")