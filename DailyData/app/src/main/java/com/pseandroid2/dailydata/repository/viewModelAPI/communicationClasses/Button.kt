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

import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.model.uielements.UIElementType

class Button(
    override val id: Int,
    val name: String,
    val columnId: Int,
    val value: Int
) : Identifiable {
    constructor(uiElement: UIElement, columnId: Int) : this(
        uiElement.id,
        TODO(),
        columnId,
        uiElement.state.toInt()
    )

    override fun deleteIsPossible(): Boolean {
        TODO("Not yet implemented")
    }

    //@throws IllegalOperationException
    override suspend fun delete() {
        TODO("Not yet implemented")
    }

    fun toDBEquivalent(): UIElement {
        return UIElement(
            id,
            UIElementType.BUTTON,
            value.toString()
        )
    }
    fun increaseValue() {
        setValue(value + 1)
    }
    fun decreaseValue() {
        setValue(value - 1)
    }
    fun setValue(value: Int) {
        TODO()
    }
}
