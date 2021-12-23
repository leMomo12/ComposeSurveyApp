package com.mnowo.composesurveyapp.di

import com.mnowo.composesurveyapp.feature_home.data.remote.HomeRemoteDb
import com.mnowo.composesurveyapp.feature_home.data.repository.HomeRepositoryImpl
import com.mnowo.composesurveyapp.feature_home.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideHomeRepository(remoteDb: HomeRemoteDb): HomeRepository {
        return HomeRepositoryImpl(remoteDb)
    }
}