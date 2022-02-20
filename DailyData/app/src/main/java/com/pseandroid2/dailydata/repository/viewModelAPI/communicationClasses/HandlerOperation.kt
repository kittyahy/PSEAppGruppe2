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

import com.pseandroid2.dailydata.repository.commandCenter.commands.CreateProject
import com.pseandroid2.dailydata.repository.commandCenter.commands.JoinOnlineProject
import com.pseandroid2.dailydata.repository.viewModelAPI.ProjectHandler

enum class HandlerOperation(val id: String, val adminOp: Boolean) {
    CREATE_PROJECT("CREATE_PROJECT", false) {
        override fun isIllegalByData(projectHandler: ProjectHandler): Boolean {
            return CreateProject.isIllegal(projectHandler)
        }
    },
    JOIN_ONLINE_PROJECT("JOIN_ONLINE_PROJECT", false) {
        override fun isIllegalByData(projectHandler: ProjectHandler): Boolean {
            return JoinOnlineProject.isIllegal(projectHandler)
        }
    };


    open fun isIllegalByData(projectHandler: ProjectHandler): Boolean = false
}