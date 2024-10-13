package com.cb.meapps.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cb.meapps.data.dao.DocumentDao
import com.cb.meapps.data.model.DocumentEntity

@Database(entities = [DocumentEntity::class], version = 1)
abstract class DayMateDatabase : RoomDatabase() {
    abstract fun documentDao(): DocumentDao
}