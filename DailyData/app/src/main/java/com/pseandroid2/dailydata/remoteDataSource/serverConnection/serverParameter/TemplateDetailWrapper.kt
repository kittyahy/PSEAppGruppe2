package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter

/**
 * A data class for uploading a template of a post. It has the template detail image and it's title
 *
 * @param templateDetailImage: The template detail image
 * @param title: The title of the post
 */
data class TemplateDetailWrapper(val templateDetailImage: List<Byte> = listOf(),
                                 val title: String = "")
