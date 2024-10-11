package com.cb.meapps.di

import android.content.Context
import com.cb.meapps.data.PreferencesDelegate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providePreferencesHelper(context: Context): PreferencesDelegate {
        return PreferencesDelegate(context)
    }
}