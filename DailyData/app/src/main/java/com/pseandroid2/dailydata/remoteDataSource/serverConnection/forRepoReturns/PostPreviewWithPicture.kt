package com.pseandroid2.dailydata.remoteDataSource.serverConnection.forRepoReturns

import android.graphics.Bitmap

/**
 * A dataclass which will be passed to the repository. It stores a single post preview
 *
 * @param id:               The id of the post
 * @param preview:          The PostPreview as a JSON
 * @param previewPicture:   The preview image of the post
 */
data class PostPreviewWithPicture(val id: Int = 0,
                                  val preview: String = "post preview",
                                  val previewPicture: Bitmap
)
