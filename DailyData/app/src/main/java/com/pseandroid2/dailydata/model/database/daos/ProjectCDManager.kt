/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/

package com.pseandroid2.dailydata.model.database.daos


import android.util.Log
import androidx.room.withTransaction
import com.pseandroid2.dailydata.model.graph.Graph
import com.pseandroid2.dailydata.model.database.AppDataBase
import com.pseandroid2.dailydata.model.database.entities.ProjectEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectSkeletonEntity
import com.pseandroid2.dailydata.model.database.entities.ProjectTemplateEntity
import com.pseandroid2.dailydata.model.notifications.Notification
import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.model.project.ProjectSkeleton
import com.pseandroid2.dailydata.model.project.ProjectTemplate
import com.pseandroid2.dailydata.model.table.TableLayout
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.model.users.NullUser
import com.pseandroid2.dailydata.model.users.User
import com.pseandroid2.dailydata.util.Consts.LOG_TAG
import com.pseandroid2.dailydata.util.SortedIntListUtil
import java.time.LocalDateTime
import java.util.SortedSet
import java.util.TreeSet

class ProjectCDManager(
    private val db: AppDataBase
) {
    private val projectDAO: ProjectDataDAO = db.projectDataDAO()
    private val templateDAO: TemplateDAO = db.templateDAO()
    private val uiDAO: UIElementDAO = db.uiElementDAO()
    private val notifDAO: NotificationsDAO = db.notificationsDAO()
    private val graphDAO: GraphDAO = db.graphDAO()
    private val graphManager: GraphCDManager = db.graphCDManager()
    private val existingIds: SortedSet<Int> = TreeSet()

    /**
     * Inserts a Project into the Database. This method does so as a transaction, i.e. it will roll
     * back any changes if an exception is thrown at any point.
     *
     * @param project The project that is to be inserted into the database
     * @return The same project that was given as a parameter, however if there were id changes
     * necessary to insert it into the database, these are reflected here.
     */
    suspend fun insertProject(project: Project): Project = db.withTransaction() {
        val newID: Int = insertProjectEntity(project)
        project.id = newID

        for (graph: Graph<*, *> in project.graphs) {
            val newGraphId: Int = graphManager.insertGraph(newID, graph)
            graph.id = newGraphId
        }

        for (notif: Notification in project.notifications) {
            val newNotifId: Int = notifDAO.insertNotification(newID, notif)
            notif.id = newNotifId
        }

        val layout: TableLayout = project.table.getLayout()
        for (col: Int in 0 until layout.getSize()) {
            for (uiElement: UIElement in layout.getUIElements(col)) {
                val newUiId: Int = uiDAO.insertUIElement(newID, col, uiElement)
                uiElement.id = newUiId
            }
        }

        for (user: User in project.users) {
            projectDAO.addUser(newID, user)
        }

        return@withTransaction project
    }

    /**
     * Deletes a project from the Database. This method does so as a Transaction, i.e. it will roll
     * back any changes if an exception occurs at any point during the deletion process.
     *
     * @param project The project that is to be deleted
     */
    suspend fun deleteProject(project: Project) = db.withTransaction {
        @Suppress("Deprecation")
        val id = project.getProjectSkeleton().id
        @Suppress("Deprecation")
        graphDAO.deleteAllGraphs(id)
        @Suppress("Deprecation")
        notifDAO.deleteAllNotifications(id)

        uiDAO.deleteAllUIElements(id)

        projectDAO.deleteAllUsers(id)

        projectDAO.deleteProjectEntityById(id)
    }

    /**
     * Inserts a new Project Template into the database.
     * @param template The template to be inserted
     * @return The id that was given to the template as unique identifier
     */
    suspend fun insertProjectTemplate(template: ProjectTemplate): Int {
        val newId = getNextId()

        @Suppress("Deprecation")
        val skeleton =
            createSkeleton(newId, template.getProjectSkeleton(), template.getTableLayout())
        val ent = ProjectTemplateEntity(skeleton, template.getCreator())
        @Suppress("Deprecation")
        templateDAO.insertProjectTemplate(ent)
        return newId
    }

    /**
     * Deletes a Project Template from the database
     * @param template The template that is to be deleted
     */
    suspend fun deleteProjectTemplate(template: ProjectTemplate) {
        @Suppress("Deprecation")
        templateDAO.deleteProjectTemplateById(template.getProjectSkeleton().id)
    }

    private fun createSkeleton(
        id: Int,
        skeleton: ProjectSkeleton,
        layout: TableLayout
    ): ProjectSkeletonEntity {
        val name: String = skeleton.name
        val desc: String = skeleton.desc
        val wallpaper: String = skeleton.path
        val color: Int = skeleton.color
        val onlineId: Long = skeleton.onlineId
        return ProjectSkeletonEntity(id, name, desc, wallpaper, color, layout.toJSON(), onlineId)
    }

    private suspend fun insertProjectEntity(project: Project): Int {
        val id = getNextId()

        @Suppress("Deprecation")
        val skeleton: ProjectSkeletonEntity =
            createSkeleton(id, project.getProjectSkeleton(), project.table.getLayout())
        val admin: User = project.admin
        Log.d(LOG_TAG, "Name: ${admin.getName()}, ID: ${admin.getId()}")
        val entity = ProjectEntity(skeleton, admin, project.isOnline)

        projectDAO.insertProjectEntity(entity)

        return id
    }

    private fun getNextId(): Int {
        return LocalDateTime.now().hashCode()
        //return SortedIntListUtil.getFirstMissingInt(ArrayList(existingIds))
    }

}