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

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.pseandroid2.dailydata.model.uielements.UIElementType
import com.pseandroid2.dailydata.model.database.entities.UIElementMap
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.util.SortedIntListUtil
import kotlinx.coroutines.flow.Flow
import java.util.SortedSet
import java.util.TreeSet

@Dao
abstract class UIElementDAO {
    private val existingIds: MutableMap<Int, out SortedSet<Int>> = mutableMapOf<Int, TreeSet<Int>>()

    @Query("SELECT * FROM uiElement WHERE projectId = :projectId")
    abstract fun getUIElements(projectId: Int): Flow<List<UIElementMap>>

    suspend fun insertUIElement(
        projectId: Int,
        columnId: Int,
        element: UIElement
    ): Int {
        val id: Int = getNextId(projectId)
        insertUIElementMap(
            UIElementMap(
                projectId,
                id,
                columnId,
                element.type.toString(),
                element.state
            )
        )
        return id
    }

    suspend fun removeUIElements(projectId: Int, vararg ids: Int) {
        for (id: Int in ids) {
            removeUIElementMap(UIElementMap(projectId, id, 0, "", ""))
        }
    }

    @Query("UPDATE uiElement SET state = :state WHERE projectId = :projectId AND id = :id")
    abstract suspend fun changeUIElementState(projectId: Int, id: Int, state: String)

    /*========================SHOULD ONLY BE CALLED FROM INSIDE THE MODEL=========================*/
    @Delete
    abstract suspend fun removeUIElementMap(vararg uiElements: UIElementMap)

    @Insert
    abstract suspend fun insertUIElementMap(uiElement: UIElementMap)

    @Query("DELETE FROM uiElement WHERE projectId = :projectId")
    abstract suspend fun deleteAllUIElements(projectId: Int)

    private fun getNextId(projectId: Int): Int {
        //Get the List of existing Ids for the project
        val list: List<Int> = ArrayList(existingIds[projectId] ?: sortedSetOf())

        //Get the next missing id
        return SortedIntListUtil.getFirstMissingInt(list)
    }
}