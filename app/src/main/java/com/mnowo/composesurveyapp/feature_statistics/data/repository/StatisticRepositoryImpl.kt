package com.mnowo.composesurveyapp.feature_statistics.data.repository

import com.mnowo.composesurveyapp.core.domain.models.SavingOwnSurvey
import com.mnowo.composesurveyapp.core.domain.models.SurveyInfo
import com.mnowo.composesurveyapp.feature_statistics.data.local.StatisticDao
import com.mnowo.composesurveyapp.feature_statistics.data.remote.StatisticRemoteDb
import com.mnowo.composesurveyapp.feature_statistics.domain.repository.StatisticRepository
import javax.inject.Inject

class StatisticRepositoryImpl @Inject constructor(
    private val remoteDb: StatisticRemoteDb,
    private val dao: StatisticDao
) : StatisticRepository {

    override suspend fun getOwnSurveys(collectionList: List<SavingOwnSurvey>): MutableList<SurveyInfo> {
        return remoteDb.getOwnSurveys(collectionList)
    }

    override suspend fun getOwnCachedSurveys(): List<SavingOwnSurvey> {
        return dao.getOwnCachedSurveys()
    }

}