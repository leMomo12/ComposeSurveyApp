package com.mnowo.composesurveyapp.di

import com.mnowo.composesurveyapp.core.data.SurveyDatabase
import com.mnowo.composesurveyapp.feature_add_survey.data.local.AddSurveyDao
import com.mnowo.composesurveyapp.feature_add_survey.data.remote.AddSurveyRemoteDb
import com.mnowo.composesurveyapp.feature_add_survey.data.repository.AddSurveyRepositoryImpl
import com.mnowo.composesurveyapp.feature_add_survey.domain.repositrory.AddSurveyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AddSurveyModule {

    @Singleton
    @Provides
    fun provideAddSurveyRepository(
        addSurveyDao: AddSurveyDao,
        addSurveyRemoteDb: AddSurveyRemoteDb
    ): AddSurveyRepository {
        return AddSurveyRepositoryImpl(
            addSurveyDao = addSurveyDao,
            addSurveyRemoteDb = addSurveyRemoteDb
        )
    }

    @Singleton
    @Provides
    fun provideAddSurveyDao(db: SurveyDatabase) = db.addSurveyDao()
}