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
package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverReturns

/**
 * A dataclass which will be received from the server. It stores a single post preview
 *
 * @param id:               The id of the post
 * @param preview:          The PostPreview as a JSON
 * @param previewPicture:   The preview image of the post
 */
data class PostPreview(
    val id: Int = 0,
    val preview: String = "post preview",
    val previewPicture: ByteArray = ByteArray(0)
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostPreview

        if (id != other.id) return false
        if (preview != other.preview) return false
        if (!previewPicture.contentEquals(other.previewPicture)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + preview.hashCode()
        result = 31 * result + previewPicture.contentHashCode()
        return result
    }
}
