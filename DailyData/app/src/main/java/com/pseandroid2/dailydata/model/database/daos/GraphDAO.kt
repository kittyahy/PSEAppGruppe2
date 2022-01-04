package com.pseandroid2.dailydata.model.database.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.pseandroid2.dailydata.model.database.entities.GraphEntity
import kotlinx.coroutines.flow.Flow

abstract class GraphDAO {
    @Query("SELECT * FROM graph WHERE projectId IN (:ids)")
    abstract fun getGraphEntityForProjects(vararg ids: Int): Flow<List<GraphEntity>>

    @Query("UPDATE graph SET path = :path WHERE projectId = :projectId AND id = :id")
    abstract fun changePath(projectId: Int, id: Int, path: String)

    /*========================SHOULD ONLY BE CALLED FROM INSIDE THE MODEL=========================*/
    @Insert
    abstract fun insertGraph(graph: GraphEntity)

    @Delete
    abstract fun deleteGraph(graph: GraphEntity)
}