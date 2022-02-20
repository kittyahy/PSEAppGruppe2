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

package com.pseandroid2.dailydata.repository.viewModelAPI.communicationClasses

import com.pseandroid2.dailydata.model.project.Project
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddGraph
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddNotification
import com.pseandroid2.dailydata.repository.commandCenter.commands.AddUser
import com.pseandroid2.dailydata.repository.commandCenter.commands.DeleteGraph
import com.pseandroid2.dailydata.repository.commandCenter.commands.DeleteNotification
import com.pseandroid2.dailydata.repository.commandCenter.commands.DeleteProject
import com.pseandroid2.dailydata.repository.commandCenter.commands.DeleteUser
import com.pseandroid2.dailydata.repository.commandCenter.commands.LeaveOnlineProject
import com.pseandroid2.dailydata.repository.commandCenter.commands.ProjectCommand
import com.pseandroid2.dailydata.repository.commandCenter.commands.PublishProject
import com.pseandroid2.dailydata.repository.commandCenter.commands.SetDescription
import com.pseandroid2.dailydata.repository.commandCenter.commands.SetTitle
import com.pseandroid2.dailydata.repository.commandCenter.commands.SetWallpaper

enum class ProjectOperation(val adminOp: Boolean, val id: String) {
    SET_PROJECT_NAME(false, "SET_PROJECT_NAME") {
        override fun isIllegalByData(project: Project): Boolean {
            return SetTitle.isIllegal(project)
        }
    },
    SET_PROJECT_DESC(false, "SET_PROJECT_DESC") {
        override fun isIllegalByData(project: Project): Boolean {
            return SetDescription.isIllegal(project)
        }
    },
    DELETE(false, "DELETE_PROJECT") {
        override fun isIllegalByData(project: Project): Boolean {
            return DeleteProject.isIllegal(project)
        }
    },
    ADD_GRAPH(false, "ADD_GRAPH") {
        override fun isIllegalByData(project: Project): Boolean {
            return AddGraph.isIllegal(project)
        }
    },
    ADD_USER(false, "ADD_USER") {
        override fun isIllegalByData(project: Project): Boolean {
            return AddUser.isIllegal(project)
        }
    },
    LEAVE_PROJECT(false, "LEAVE_PROJECT") {
        override fun isIllegalByData(project: Project): Boolean {
            return LeaveOnlineProject.isIllegal(project)
        }
    },
    SET_ADMIN(false, "SET_ADMIN") {
        override fun isIllegalByData(project: Project): Boolean {
            return TODO("SetAdmin isIllegal")
        }
    },
    SET_COLOR(false, "SET_COLOR") {
        override fun isIllegalByData(project: Project): Boolean {
            return SetWallpaper.isIllegal(project)
        }
    },
    CHANGE_NOTIFICATION(false, "CHANGE_NOTIFICATION") {
        override fun isIllegalByData(project: Project): Boolean {
            return TODO("SetNotification isIllegal")
        }
    },
    ADD_NOTIFICATION(false, "ADD_NOTIFICATION") {
        override fun isIllegalByData(project: Project): Boolean {
            return AddNotification.isIllegal(project)
        }
    },
    DELETE_NOTIFICATION(false, "DELETE_NOTIFICATION") {
        override fun isIllegalByData(project: Project): Boolean {
            return DeleteNotification.isIllegal(project)
        }
    },
    PUBLISH_PROJECT(false, "PUBLISH_PROJECT") {
        override fun isIllegalByData(project: Project): Boolean {
            return PublishProject.isIllegal(project)
        }
    },
    DELETE_USER(false, "DELETE_USER") {
        override fun isIllegalByData(project: Project): Boolean {
            return DeleteUser.isIllegal(project)
        }
    },
    DELETE_GRAPH(false, "DELETE_GRAPH") {
        override fun isIllegalByData(project: Project): Boolean {
            return DeleteGraph.isIllegal(project)
        }
    },
    SET_GRAPH(false, "SET_GRAPH") {
        override fun isIllegalByData(project: Project): Boolean {
            return ProjectCommand.isIllegal() //Todo replace with valid proof
        }
    };


    open fun isIllegalByData(project: Project): Boolean = false
}