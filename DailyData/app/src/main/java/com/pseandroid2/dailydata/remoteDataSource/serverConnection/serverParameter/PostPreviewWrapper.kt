package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter

/**
 * A data class for uploading posts. It has the post preview picture and it's title
 *
 * @param previewPicture: The preview picture of the post
 * @param title: The title of the post
 */
data class PostPreviewWrapper(val previewPicture: List<Byte> = listOf(),
                              val title: String = "")
