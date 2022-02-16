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
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.pseandroid2.dailydata.model.database.entities.UIElementMap
import com.pseandroid2.dailydata.model.uielements.UIElement
import com.pseandroid2.dailydata.util.Consts.LOG_TAG
import com.pseandroid2.dailydata.util.SortedIntListUtil
import kotlinx.coroutines.flow.Flow
import java.util.SortedSet
import java.util.TreeSet

/**
 * This class provides Methods and Queries to saves and change and remove UIElements for their table.
 */
@Dao
abstract class UIElementDAO {
    private val existingIds: MutableMap<Int, SortedSet<Int>> = mutableMapOf()

    /**
     * It provides all UiElements which belong to the given project.
     */
    @Query("SELECT * FROM uiElement WHERE projectId = :projectId")
    abstract fun getUIElements(projectId: Int): Flow<List<UIElementMap>>

    /**
     * It saves the given UiElement and returns its id.
     */
    suspend fun insertUIElement(
        projectId: Int,
        columnId: Int,
        element: UIElement
    ): Int {
        Log.d(LOG_TAG, "in insert UI Element")
        val id: Int = getNextId(projectId)
        Log.d(LOG_TAG, "Element: " + element.name + " ProjektID: " + projectId + " Id: " + id)
        insertUIElementMap(
            UIElementMap(
                projectId,
                id,
                columnId,
                element.type.toString(),
                element.name,
                element.state
            )
        )

        Log.d(LOG_TAG, "ExsistingIds" + existingIds[projectId] + " id: " + id)

        if (existingIds[projectId] == null) {
            existingIds[projectId] = sortedSetOf()
        }
        existingIds[projectId]!!.add(id)

        return id
    }

    /**
     * It deletes  UiElements specified by their ids, which belong to a specified project.
     */
    @Query("DELETE FROM uiElement WHERE projectId = :projectId AND id IN (:ids)")
    abstract suspend fun removeUIElements(projectId: Int, vararg ids: Int)

    /**
     * It changes the state to the given state of a specified UiElement in a specified project.
     */
    @Query("UPDATE uiElement SET state = :state WHERE projectId = :projectId AND id = :id")
    abstract suspend fun changeUIElementState(projectId: Int, id: Int, state: String)

    /**
     * It changes the name to the given name  of a specified UiElement in a specified project.
     */
    @Query("UPDATE uiElement SET name = :name WHERE projectId = :projectId AND id = :id")
    abstract suspend fun changeUIElementName(projectId: Int, id: Int, name: String)

    /*========================SHOULD ONLY BE CALLED FROM INSIDE THE MODEL=========================*/
    /**
     * It removes the given UiElementMap from the table.
     */
    @Deprecated("This method should only be used from within the model, use removeUIElements instead")
    @Delete
    abstract suspend fun removeUIElementMap(vararg uiElements: UIElementMap)

    /**
     * It inserts the given UiElementMap to the table.
     */
    @Deprecated("This method should only be used from within the model, use insertUIElement instead")
    @Insert
    abstract suspend fun insertUIElementMap(uiElement: UIElementMap)

    /**
     * It deletes all UIElements, which belong to the specified project.
     */
    @Deprecated("This method should only be used from within the model")
    @Query("DELETE FROM uiElement WHERE projectId = :projectId")
    abstract suspend fun deleteAllUIElements(projectId: Int)

    private fun getNextId(projectId: Int): Int {
        //Get the List of existing Ids for the project
        val list: List<Int> = ArrayList(existingIds[projectId] ?: sortedSetOf())

        Log.d(LOG_TAG, list.toString())
        //Get the next missing id
        return SortedIntListUtil.getFirstMissingInt(list)
    }

}