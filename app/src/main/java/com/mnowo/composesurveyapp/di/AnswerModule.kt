package com.mnowo.composesurveyapp.di

import com.mnowo.composesurveyapp.core.data.SurveyDatabase
import com.mnowo.composesurveyapp.feature_answer.data.local.AnswerDao
import com.mnowo.composesurveyapp.feature_answer.data.remote.AnswerRemoteDb
import com.mnowo.composesurveyapp.feature_answer.data.repository.AnswerRepositoryImpl
import com.mnowo.composesurveyapp.feature_answer.domain.repository.AnswerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnswerModule {

    @Singleton
    @Provides
    fun provideAnswerRepository(
        answerRemoteDb: AnswerRemoteDb,
        answerDao: AnswerDao
    ): AnswerRepository {
        return AnswerRepositoryImpl(
            answerRemoteDb = answerRemoteDb,
            answerDao = answerDao
        )
    }

    @Singleton
    @Provides
    fun provideAnswerDao(db: SurveyDatabase) = db.answerDao()


}