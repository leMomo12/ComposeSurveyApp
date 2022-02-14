package com.mnowo.composesurveyapp.feature_statistics.data.repository

import com.mnowo.composesurveyapp.feature_statistics.data.local.StatisticDao
import com.mnowo.composesurveyapp.feature_statistics.data.remote.StatisticRemoteDb
import com.mnowo.composesurveyapp.feature_statistics.domain.StatisticRepository
import javax.inject.Inject

class StatisticRepositoryImpl @Inject constructor(
    private val remoteDb: StatisticRemoteDb,
    private val dao: StatisticDao
) : StatisticRepository {

}