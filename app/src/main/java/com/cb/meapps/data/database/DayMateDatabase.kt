package com.cb.meapps.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cb.meapps.data.dao.AdditionalPaymentDao
import com.cb.meapps.data.dao.CardDao
import com.cb.meapps.data.dao.DocumentDao
import com.cb.meapps.data.dao.FuelTrackerDao
import com.cb.meapps.data.model.AdditionalPaymentEntity
import com.cb.meapps.data.model.CardEntity
import com.cb.meapps.data.model.DocumentEntity
import com.cb.meapps.data.model.FuelTrackerEntity

@Database(
    entities = [DocumentEntity::class, CardEntity::class, FuelTrackerEntity::class, AdditionalPaymentEntity::class],
    version = 6
)
abstract class DayMateDatabase : RoomDatabase() {
    abstract fun documentDao(): DocumentDao
    abstract fun cardDao(): CardDao
    abstract fun fuelTrackerDao(): FuelTrackerDao
    abstract fun additionalPaymentDao() : AdditionalPaymentDao
}