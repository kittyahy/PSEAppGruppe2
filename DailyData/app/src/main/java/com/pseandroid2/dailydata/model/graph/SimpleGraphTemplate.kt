package com.pseandroid2.dailydata.model.graph

import android.graphics.Bitmap
import com.pseandroid2.dailydata.model.database.entities.GraphData
import com.pseandroid2.dailydata.model.database.entities.GraphTemplateData
import com.pseandroid2.dailydata.model.settings.MapSettings
import com.pseandroid2.dailydata.model.settings.Settings
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
    constructor(data: GraphTemplateData) : this(
        data.id,
        data.name,
        data.description,
        data.type,
        data.creator,
        data.onlineId,
        background = data.color
    )

    override var customizing: Settings = MapSettings(mapOf())

    override fun addSetting(key: String, value: String) {
        customizing[key] = value
    }

    override fun getWallpaper(): Bitmap {
        TODO("Not yet implemented")
    }
}