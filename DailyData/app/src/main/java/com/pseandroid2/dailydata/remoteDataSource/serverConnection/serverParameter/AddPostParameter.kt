package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter

/**
 * @param postPreview: The preview of the post as JSON
 * @param projectTemplate: The project template as JSON
 * (first)Template + (second)Preview // TODO: Überarbeite JAVADOC
 * @param graphTemplate: The graph templates as Collection of JSONs
 */

data class AddPostParameter(val postPreview: String, var pair: Pair<String, String> = Pair("", ""),
                            val graphTemplate: Collection<Pair<String, String>>)
