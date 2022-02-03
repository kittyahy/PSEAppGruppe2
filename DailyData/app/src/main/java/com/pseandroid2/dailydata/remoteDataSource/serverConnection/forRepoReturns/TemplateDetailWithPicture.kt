package com.pseandroid2.dailydata.remoteDataSource.serverConnection.forRepoReturns

import android.graphics.Bitmap

data class TemplateDetailWithPicture(val id: Int = 0,
                                     val title: String = "template detail",
                                     val detailImage: Bitmap,
                                     val projectTemplate: Boolean = false)
