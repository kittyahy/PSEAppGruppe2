package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter

/**
 * @param postPreview: The preview of the post as JSON
 * @param projectTemplate:
 * @param graphTemplate: The graph templates as Collection of pairs of graph templates as JSONs and the graph template previews
 */

data class AddPostParameter(val postPreview: String = "", var projectTemplate: Pair<String, String> = Pair("", ""),
                            val graphTemplates: Collection<Pair<String, String>> = listOf(Pair("", "")))
