package com.pseandroid2.dailydata.remoteDataSource.appLinks
import com.pseandroid2.dailydata.remoteDataSource.appLinks.Hashids

class JoinProjectLinkManager {
    // Hash function
    private val hashids = Hashids("dasSicherstePasswortDerWelt", 4)

    /**
     * // TODO
     *  @return returns the link. If error returns ""
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

    fun decodePostID(postIDToDecode: String): Long {
        val decoded = hashids.decode(postIDToDecode)
        if (decoded.isNotEmpty()) {
            if (decoded.elementAt(0) != null) {
                return decoded.elementAt(0)
            }
        }
        return -1
    }
}