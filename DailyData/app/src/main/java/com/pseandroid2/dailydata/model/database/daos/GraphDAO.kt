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
import com.pseandroid2.dailydata.model.Graph
import com.pseandroid2.dailydata.model.database.entities.GraphData
import com.pseandroid2.dailydata.model.database.entities.GraphEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Dao
abstract class GraphDAO {
    @Query("SELECT id, dataTransformation, type, path FROM graph WHERE projectId = :id")
    abstract fun getGraphDataForProject(id: Int): Flow<List<GraphData>>

    @Query("UPDATE graph SET path = :path WHERE projectId = :projectId AND id = :id")
    abstract suspend fun changePath(projectId: Int, id: Int, path: String)

    /*========================SHOULD ONLY BE CALLED FROM INSIDE THE MODEL=========================*/

    @Deprecated("This method should only be used from within the model, use GraphCDManager.insertGraph instead")
    @Insert
    abstract suspend fun insertGraph(graph: GraphEntity)

    @Deprecated("This method should only be used from within the model, use GraphCDManager.deleteGraph instead")
    @Delete
    abstract suspend fun deleteGraph(graph: GraphEntity)

    @Deprecated("This method should only be used from within the model")
    @Query("DELETE FROM graph WHERE projectId = :projectId")
    abstract suspend fun deleteAllGraphs(projectId: Int)
}