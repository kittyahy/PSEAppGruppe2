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

/**
 * A data class for uploading a template of a post. It has the template detail image and it's title
 *
 * @param templateDetailImage:  The template detail image
 * @param title:                The title of the post
 */
data class TemplateDetailWrapper(val templateDetailImage: ByteArray =  ByteArray(0),
                                 val title: String = "template") {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TemplateDetailWrapper

        if (!templateDetailImage.contentEquals(other.templateDetailImage)) return false
        if (title != other.title) return false

        return true
    }

    override fun hashCode(): Int {
        var result = templateDetailImage.contentHashCode()
        result = 31 * result + title.hashCode()
        return result
    }
}
