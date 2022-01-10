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

    fun insertUIElement(
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

    fun removeUIElements(projectId: Int, vararg ids: Int) {
        for (id: Int in ids) {
            removeUIElementMap(UIElementMap(projectId, id, 0, "", ""))
        }
    }

    @Query("UPDATE uiElement SET state = :state WHERE projectId = :projectId AND id = :id")
    abstract fun changeUIElementState(projectId: Int, id: Int, state: String)

    /*========================SHOULD ONLY BE CALLED FROM INSIDE THE MODEL=========================*/
    @Delete
    abstract fun removeUIElementMap(vararg uiElements: UIElementMap)

    @Insert
    abstract fun insertUIElementMap(uiElement: UIElementMap)

    private fun getNextId(projectId: Int): Int {
        //Get the List of existing Ids for the project
        val list: List<Int> = ArrayList(existingIds[projectId] ?: sortedSetOf())

        //Get the next missing id
        return SortedIntListUtil.getFirstMissingInt(list)
    }
}