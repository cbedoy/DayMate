package com.cb.meapps.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cb.meapps.data.dao.CardDao
import com.cb.meapps.data.dao.DocumentDao
import com.cb.meapps.data.model.CardEntity
import com.cb.meapps.data.model.DocumentEntity

@Database(
    entities = [DocumentEntity::class, CardEntity::class],
    version = 3
)
abstract class DayMateDatabase : RoomDatabase() {
    abstract fun documentDao(): DocumentDao
    abstract fun cardDao(): CardDao
}