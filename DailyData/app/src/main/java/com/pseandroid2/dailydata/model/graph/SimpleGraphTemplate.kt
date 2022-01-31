package com.pseandroid2.dailydata.model.graph

import android.graphics.Bitmap
import com.pseandroid2.dailydata.model.settings.MapSettings
import com.pseandroid2.dailydata.model.users.NullUser
import com.pseandroid2.dailydata.model.users.User

class SimpleGraphTemplate(
    override val id: Int = -1,
    override val name: String = "",
    override val desc: String = "",
    override val type: GraphType,
    override val creator: User = NullUser(),
    override val onlineId: Long = -1,
    override val path: String = "",
    override val background: Int = -1
) : GraphTemplate {
    private val customizing = MapSettings(mapOf())
    override fun getWallpaper(): Bitmap {
        TODO("Not yet implemented")
    }

    override fun getCustomizing() = customizing
}