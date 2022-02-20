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

package com.pseandroid2.dailydata.ui.project.data.input

sealed class ProjectDataInputScreenEvent {


    data class OnCreate(val projectId : Int) : ProjectDataInputScreenEvent()

    object OnDescriptionClick : ProjectDataInputScreenEvent()

    data class OnButtonClickInc(val id : Int) : ProjectDataInputScreenEvent()
    data class OnButtonClickDec(val id : Int) : ProjectDataInputScreenEvent()
    data class OnButtonClickAdd(val id : Int) : ProjectDataInputScreenEvent()

    data class OnColumnChange(val index : Int, val value : String) : ProjectDataInputScreenEvent()
    object OnColumnAdd : ProjectDataInputScreenEvent()

    data class OnRowDialogShow(val index : Int) : ProjectDataInputScreenEvent()
    object OnCloseRowDialog : ProjectDataInputScreenEvent()
    object OnRowModifyClick : ProjectDataInputScreenEvent()
    object OnRowDeleteClick : ProjectDataInputScreenEvent()

}
