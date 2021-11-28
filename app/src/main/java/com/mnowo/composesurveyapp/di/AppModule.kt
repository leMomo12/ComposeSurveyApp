package com.mnowo.composesurveyapp.di

import android.content.Context
import androidx.room.Room
import com.mnowo.composesurveyapp.core.data.SurveyDatabase
import com.mnowo.composesurveyapp.core.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): SurveyDatabase {
        return Room.databaseBuilder(
            appContext,
            SurveyDatabase::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }
}