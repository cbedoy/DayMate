package com.cb.meapps.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.cb.meapps.data.PreferencesDelegate
import com.cb.meapps.data.dao.CardDao
import com.cb.meapps.data.dao.DocumentDao
import com.cb.meapps.data.database.DayMateDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Provides
    @Singleton
    fun providePreferencesHelper(context: Context): PreferencesDelegate {
        return PreferencesDelegate(context)
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): DayMateDatabase {
        return Room.databaseBuilder(
            app,
            DayMateDatabase::class.java,
            "document_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideDocumentDao(db: DayMateDatabase): DocumentDao {
        return db.documentDao()
    }

    @Provides
    fun provideCardDao(db: DayMateDatabase): CardDao {
        return db.cardDao()
    }
}