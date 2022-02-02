package com.pseandroid2.dailydata.remoteDataSource.serverConnection

/**
 * Contains important details for an online project
 *
 * @param projectName:        The name of the online project
 * @param projectDescription: The description of the online project
 * @param columns:            Contains a pair for each column in the project table. This pair structure is: Pair<ColumnName, DataTypeThatIsSavedInTheColumn>
 * @param notifications:      Contains the the notifications of a project
 */
data class ProjectDetails(val projectName: String = "", val projectDescription: String = "",
                          val columns: Pair<String, String> = Pair("columnName", "savedDataType"),
                          val notifications: Collection<String>)
