package com.mnowo.composesurveyapp.di

import com.mnowo.composesurveyapp.core.data.SurveyDatabase
import com.mnowo.composesurveyapp.feature_statistics.data.local.StatisticDao
import com.mnowo.composesurveyapp.feature_statistics.data.remote.StatisticRemoteDb
import com.mnowo.composesurveyapp.feature_statistics.data.repository.StatisticRepositoryImpl
import com.mnowo.composesurveyapp.feature_statistics.domain.repository.StatisticRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StatisticsModule {

    @Singleton
    @Provides
    fun provideStatisticRepository(
        remoteDb: StatisticRemoteDb,
        dao: StatisticDao
    ) : StatisticRepository {
        return StatisticRepositoryImpl(
            remoteDb = remoteDb,
            dao = dao
        )
    }

    @Singleton
    @Provides
    fun provideStatisticDao(db: SurveyDatabase) = db.statisticDao()

}