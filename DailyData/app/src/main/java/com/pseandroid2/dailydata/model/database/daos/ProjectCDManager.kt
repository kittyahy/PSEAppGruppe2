package com.pseandroid2.dailydata.model.database.daos

import com.pseandroid2.dailydata.model.Project
import com.pseandroid2.dailydata.model.ProjectSkeleton
import com.pseandroid2.dailydata.model.ProjectTemplate
import com.pseandroid2.dailydata.model.User
import com.pseandroid2.dailydata.model.database.entities.ProjectEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectSkeletonEntity
import java.util.SortedSet
import java.util.TreeSet

class ProjectCDManager private constructor() {

    //ProjectCDManager is a Singleton
    companion object {
        private var instance: ProjectCDManager? = null

        fun getInstance(): ProjectCDManager {
            return instance ?: ProjectCDManager()
        }
    }

    val existingIds: SortedSet<Int> = TreeSet<Int>()

    fun insertProject(project: Project): Project {
        val newID: Int = insertProjectEntity(project)
        project.getProjectSkeleton().setID(newID)

        //TODO insert rest of project stuff into database

        return project
    }

    fun deleteProject(project: Project) {
        //TODO
    }

    fun insertProjectTemplate(template: ProjectTemplate): Int {
        //TODO
        return 0
    }

    fun deleteProjectTemplate(template: ProjectTemplate) {
        //TODO
    }

    private fun isTemplate(id: Int): Boolean {
        //TODO
        return false
    }

    private fun getNextId(): Int {
        //TODO
        return 0
    }

    private fun createSkeleton(project: Project): ProjectSkeletonEntity {
        val skeleton: ProjectSkeleton = project.getProjectSkeleton()
        val name: String = skeleton.getName()
        val desc: String = skeleton.getDescription()
        val wallpaper: String = skeleton.getWallpaperPath()
        val layout: String =
            project.getTable().getLayout().toJSON()
        return ProjectSkeletonEntity(name, desc, wallpaper, layout)
    }

    private fun insertProjectEntity(project: Project): Int {
        val skeleton: ProjectSkeletonEntity = createSkeleton(project)
        val admin: User = project.getAdmin()
        val onlineId: Long = project.getOnlineId()
        val id = getNextId()
        val entity: ProjectEntity = ProjectEntity(id, skeleton, admin, onlineId)

        //TODO insert entity via DAO

        return id
    }

}