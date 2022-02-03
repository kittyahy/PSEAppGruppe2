package com.pseandroid2.dailydata.remoteDataSource.serverConnection.forRepoReturns

import android.graphics.Bitmap

/**
 * A dataclass which will be passed to the repository. It stores the details of a single template (project template or graph template)
 *
 * @param id:                   The id of the template
 * @param title:                The title of the template
 * @param projectTemplate:      If true, than a projectTemplate is described. If false, than a graphTemplate is described
 * @param detailImage:          The detail image of the template
 */
data class TemplateDetailWithPicture(val id: Int = 0,
                                     val title: String = "template detail",
                                     val detailImage: Bitmap,
                                     val projectTemplate: Boolean = false)
