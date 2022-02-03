/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/
package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter
import com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter.PostPreviewWrapper

/**
 * A dataclass which will be send to the server for adding posts. Contains the parameters needed to the server for adding a new post
 *
 * @param postPreview: The post preview as a PostPreviewWrapper object
 * @param projectTemplate: The project template. The first entry is the Template, the second is the detailView of the template
 * @param graphTemplates: The graph templates. The first entry of the Pair is the Template, the second is the detailView of the template
 */

data class AddPostParameter(val postPreview: PostPreviewWrapper =  PostPreviewWrapper(),
                            val projectTemplate: Pair<String, TemplateDetailWrapper> = Pair("", TemplateDetailWrapper()),
                            val graphTemplates: List<Pair<String, TemplateDetailWrapper>> = listOf(Pair("", TemplateDetailWrapper())))
