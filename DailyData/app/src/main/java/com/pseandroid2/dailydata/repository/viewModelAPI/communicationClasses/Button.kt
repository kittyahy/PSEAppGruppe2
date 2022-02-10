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

import com.pseandroid2.dailydata.model.project.ProjectBuilder
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.model.uielements.UIElementType
import com.pseandroid2.dailydata.repository.commandCenter.ExecuteQueue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import com.pseandroid2.dailydata.model.project.Project as ModelProject
/**
 * Button class that handles its specific interaction with ViewModel.
 */
class Button(
    override var id: Int,
    val name: String,
    val columnId: Int,
    val value: Int
) : Identifiable, Convertible<UIElement> {
    override lateinit var executeQueue: ExecuteQueue
    override lateinit var project: Project

    constructor(uiElement: UIElement, columnId: Int) : this(
        uiElement.id,
        uiElement.name,
        columnId,
        uiElement.state.toInt()
    )

    override fun deleteIsPossible(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    //@throws IllegalOperationException
    override suspend fun delete() {
        TODO("Not yet implemented")
    }

    override fun toDBEquivalent(): UIElement {
        return UIElement(
            id,
            UIElementType.BUTTON,
            name,
            value.toString()
        )
    }

    fun increaseValueIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>()
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    suspend fun increaseValue() {
        setValue(value + 1)
    }

    fun decreaseValueIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>()
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    suspend fun decreaseValue() {
        setValue(value - 1)
    }

    fun setValueIsPossible(): Flow<Boolean> {
        //Todo replace with valid proof
        val flow = MutableSharedFlow<Boolean>()
        runBlocking {
            flow.emit(true)
        }
        return flow
    }

    suspend fun setValue(value: Int) {
        TODO("setValue")
    }

    override fun addYourself(builder: ProjectBuilder<out ModelProject>) {
        TODO("Not yet implemented")
    }
}
