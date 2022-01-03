package com.pseandroid2.dailydata.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pseandroid2.dailydata.model.database.entities.ProjectEntity

@Database(entities = [ProjectEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

}