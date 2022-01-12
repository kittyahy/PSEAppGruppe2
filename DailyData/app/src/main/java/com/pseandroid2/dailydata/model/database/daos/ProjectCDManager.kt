package com.pseandroid2.dailydata.model.database.daos


import com.pseandroid2.dailydata.model.Graph
import com.pseandroid2.dailydata.model.Project
import com.pseandroid2.dailydata.model.ProjectSkeleton
import com.pseandroid2.dailydata.model.ProjectTemplate
import com.pseandroid2.dailydata.model.TableLayout
import com.pseandroid2.dailydata.model.User
import com.pseandroid2.dailydata.model.database.entities.ProjectEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectSkeletonEntity
import com.pseandroid2.dailydata.model.notifications.Notification
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.util.SortedIntListUtil
import java.util.SortedSet
import java.util.TreeSet

class ProjectCDManager private constructor(
    private val projectDAO: ProjectDataDAO,
    private val uiDAO: UIElementDAO,
    private val notifDAO: NotificationsDAO,
    private val graphManager: GraphCDManager
) {

    private val existingIds: SortedSet<Int> = TreeSet()

    suspend fun insertProject(project: Project): Project {
        val newID: Int = insertProjectEntity(project)
        project.getProjectSkeleton().setID(newID)

        for (graph: Graph in project.getProjectSkeleton().getGraphs()) {
            val newGraphId: Int = graphManager.insertGraph(newID, graph)
            graph.id = newGraphId
        }

        for (notif: Notification in project.getProjectSkeleton().getNotifications()) {
            val newNotifId: Int = notifDAO.insertNotification(newID, notif)
            notif.id = newNotifId
        }

        val layout: TableLayout = project.getTable().getLayout()
        for (col: Int in 0 until layout.getSize()) {
            for (uiElement: UIElement in layout.getUIElements(col)) {
                val newUiId: Int = uiDAO.insertUIElement(newID, col, uiElement)
                uiElement.id = newUiId
            }
        }

        for (user: User in project.getUsers()) {
            projectDAO.addUser(newID, user)
        }

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
        return SortedIntListUtil.getFirstMissingInt(ArrayList(existingIds))
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
        val entity = ProjectEntity(id, skeleton, admin, onlineId)

        //projectDAO.insertProjectEntity(entity)

        return id
    }

}