package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter

/**
 * @param postPreview: The preview of the post as JSON
 * @param projectTemplate: The project template as JSON
 * @param graphTemplate: The graph templates as Collection of JSONs
 */
data class AddPostParameter(val postPreview: String, val projectTemplate: String, val graphTemplate: Collection<String>)
