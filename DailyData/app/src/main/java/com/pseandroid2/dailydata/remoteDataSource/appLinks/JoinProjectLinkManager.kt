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
package com.pseandroid2.dailydata.remoteDataSource.appLinks
import android.util.Log
import com.pseandroid2.dailydata.remoteDataSource.appLinks.Hashids

class JoinProjectLinkManager {
    // Hash function
    private val hashids = Hashids("dasSicherstePasswortDerWelt", 4)

    /**
     *  Creates a link that has the encrypted project id embedded
     *
     *  @param postID: The postID that should be embedded into the link
     *  @return Returns the link. If error returns ""
     */
    fun createLink(postID: Long): String {
        val encodedLong = encodeLong(postID)
        if (encodedLong == "") {
            return ""
        }
        return "https://https://www.dailydata.com/?projectid="+encodeLong(postID)
    }


    /**
     * Encodes the postID into a hash
     *
     * @param postIDToEncode: The Long that should be encoded. It has to be in [1, uLongsize-1] // TODO rework comment
     * @return String: returns the encoded long as a hash. Returns "" if error
     */
    private fun encodeLong(postIDToEncode: Long): String {
        return try {
            hashids.encode(postIDToEncode)
        } catch (e: ArrayIndexOutOfBoundsException) {
            ""
        }
    }

    /**
     * decodes the postID
     *
     * @param postIDToDecode: The postID that should be decoded
     * @return Long: The decoded postID. Returns -1 if an error occurred
     */
    fun decodePostID(postIDToDecode: String): Long {
        try {
            val decoded = hashids.decode(postIDToDecode)
            if (decoded.isNotEmpty()) {
                return decoded.elementAt(0)
            }
        } catch (ex: Exception) {
            when (ex) {
                is NoSuchElementException, is ArrayIndexOutOfBoundsException -> {
                    Log.e("JoinProjectLinkError","There is no project ID that belongs to the input")
                }
                else -> throw ex
            }
        }

        return -1
    }
}