package com.pseandroid2.dailydata.remoteDataSource.serverConnection.forRepoReturns

import android.graphics.Bitmap

data class PostPreviewWithPicture(val id: Int = 0,
                                  val preview: String = "post preview",
                                  val previewPicture: Bitmap
)
