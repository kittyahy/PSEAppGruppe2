package com.pseandroid2.dailydata.model

import android.graphics.drawable.Drawable
import com.pseandroid2.dailydata.model.notifications.Notification

/**
 * Contains all data of one specific Project
 */
interface Project {

    /**
     * @return The ProjectSkeleton of this Project
     */
    fun getProjectSkeleton(): ProjectSkeleton

    fun getTable(): Table

    fun getAdmin(): User

    fun isOnline(): Boolean

    fun getOnlineId(): Long

    fun getUsers(): Collection<User>

    abstract class DataTransformation<O>(private val table: Table) {

        abstract fun recalculate(): List<Any>

    }

}

interface ProjectSkeleton {

    fun getID(): Int
    fun setID(id: Int)

    fun getName(): String

    fun getDescription(): String

    fun getWallpaper(): Drawable
    fun getWallpaperPath(): String

    fun getGraphs(): Collection<Graph>

    fun getProjectSettings(): Settings

    fun getNotifications(): Collection<Notification>

}

interface ProjectTemplate {

    fun getProjectSkeleton(): ProjectSkeleton

    fun getTableLayout(): TableLayout

    fun getCreator(): User

}