package com.mnowo.composesurveyapp.di

import com.mnowo.composesurveyapp.core.data.SurveyDatabase
import com.mnowo.composesurveyapp.feature_auth.data.local.AuthDao
import com.mnowo.composesurveyapp.feature_auth.data.remote.AuthRemoteDb
import com.mnowo.composesurveyapp.feature_auth.data.repository.AuthRepositoryImpl
import com.mnowo.composesurveyapp.feature_auth.domain.repository.AuthRepository
import com.mnowo.composesurveyapp.feature_auth.domain.use_case.LoginUseCase
import com.mnowo.composesurveyapp.feature_auth.domain.use_case.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(authRepository: AuthRepository): RegisterUseCase {
        return RegisterUseCase(authRepository)
    }


    @Provides
    @Singleton
    fun provideAuthRepository(authRemoteDb: AuthRemoteDb, authDao: AuthDao): AuthRepository {
        return AuthRepositoryImpl(authRemoteDb = authRemoteDb, authDao = authDao)
    }

    @Singleton
    @Provides
    fun provideAuthDao(db: SurveyDatabase) = db.authDao()
}