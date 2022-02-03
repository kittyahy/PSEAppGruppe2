package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter

/**
 * A data class for uploading posts. It has the post preview picture and it's title
 *
 * @param previewPicture: The preview picture of the post
 * @param title: The title of the post
 */
data class PostPreviewWrapper(val previewPicture: ByteArray = ByteArray(0),
                              val title: String = "") {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostPreviewWrapper

        if (!previewPicture.contentEquals(other.previewPicture)) return false
        if (title != other.title) return false

        return true
    }

    override fun hashCode(): Int {
        var result = previewPicture.contentHashCode()
        result = 31 * result + title.hashCode()
        return result
    }
}
