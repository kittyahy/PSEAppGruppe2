package com.pseandroid2.dailydata.remoteDataSource.serverConnection.serverParameter

/**
 * A data class for uploading a template of a post. It has the template detail image and it's title
 *
 * @param templateDetailImage: The template detail image
 * @param title: The title of the post
 */
data class TemplateDetailWrapper(val templateDetailImage: ByteArray =  ByteArray(0),
                                 val title: String = "") {

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
