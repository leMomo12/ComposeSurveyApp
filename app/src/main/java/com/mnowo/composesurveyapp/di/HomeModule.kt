package com.mnowo.composesurveyapp.di

import com.mnowo.composesurveyapp.feature_auth.data.remote.AuthRemoteDb
import com.mnowo.composesurveyapp.feature_auth.data.repository.AuthRepositoryImpl
import com.mnowo.composesurveyapp.feature_auth.domain.repository.AuthRepository
import com.mnowo.composesurveyapp.feature_home.data.repository.HomeRepositoryImpl
import com.mnowo.composesurveyapp.feature_home.domain.HomeRepository
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
    fun provideHomeRepository(): HomeRepository {
        return HomeRepositoryImpl()
    }
}