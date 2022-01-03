package com.pseandroid2.dailydata.model

import android.graphics.drawable.Drawable

/**
 * Contains all data of one specific Project
 */
interface Project {

    /**
     * @return The ProjectSkeleton of this Project
     */
    public fun getProjectSkeleton(): ProjectSkeleton

    public fun getTable(): Table

    public fun getAdmin(): User

    public fun isOnline(): Boolean

    public fun getOnlineId(): Long

    public fun getUsers(): Collection<User>

    public abstract class DataTransformation<O>(private val table: Table) {

        public abstract fun recalculate(): List<Any>

    }

}

interface ProjectSkeleton {

    public fun getID(): Int
    public fun setID(id: Int)

    public fun getName(): String

    public fun getDescription(): String

    public fun getWallpaper(): Drawable
    public fun getWallpaperPath(): String

    public fun getGraphs(): Collection<Graph>

    public fun getProjectSettings(): Settings

    public fun getNotifications(): Collection<Notification>

}

interface ProjectTemplate {

    public fun getProjectSkeleton(): ProjectSkeleton

    public fun getTableLayout(): TableLayout

    public fun getCreator(): User

}