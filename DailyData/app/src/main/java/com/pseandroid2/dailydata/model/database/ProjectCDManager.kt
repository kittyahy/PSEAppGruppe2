package com.pseandroid2.dailydata.model.database

import com.pseandroid2.dailydata.model.Project
import com.pseandroid2.dailydata.model.ProjectTemplate
import com.pseandroid2.dailydata.model.User
import com.pseandroid2.dailydata.model.database.entities.ProjectEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectSkeletonEntity
import java.util.SortedSet
import java.util.TreeSet

class ProjectCDManager {

    //TODO should this be a singleton?

    val existingIds: SortedSet<Int> = TreeSet<Int>()

    public fun insertProject(project: Project): Project {
        val skeleton: ProjectSkeletonEntity = createSkeleton(project)
        val admin: User = project.getAdmin()
        val onlineId: Long = project.getOnlineId()
        val id = getNextId()
        val entity: ProjectEntity = ProjectEntity(id, skeleton, admin, onlineId)

        //TODO insert entity via DAO

        return project
    }

    public fun deleteProject(project: Project) {
        //TODO
    }

    public fun insertProjectTemplate(template: ProjectTemplate): Int {
        //TODO
        return 0
    }

    public fun deleteProjectTemplate(template: ProjectTemplate) {
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
        //TODO
        return ProjectSkeletonEntity("", "", "", "")
    }

    private fun insertProjectEntity(project: Project): Int {
        //TODO
        return 0
    }

}