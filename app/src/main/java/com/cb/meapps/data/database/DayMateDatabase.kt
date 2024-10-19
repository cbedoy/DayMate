package com.cb.meapps.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cb.meapps.data.dao.CardDao
import com.cb.meapps.data.dao.DocumentDao
import com.cb.meapps.data.dao.FuelTrackerDao
import com.cb.meapps.data.model.CardEntity
import com.cb.meapps.data.model.DocumentEntity
import com.cb.meapps.data.model.FuelTrackerEntity

@Database(
    entities = [DocumentEntity::class, CardEntity::class, FuelTrackerEntity::class],
    version = 5
)
abstract class DayMateDatabase : RoomDatabase() {
    abstract fun documentDao(): DocumentDao
    abstract fun cardDao(): CardDao
    abstract fun fuelTrackerDao(): FuelTrackerDao
}