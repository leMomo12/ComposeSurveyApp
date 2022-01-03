package com.mnowo.composesurveyapp.feature_home.data.repository

import com.mnowo.composesurveyapp.feature_home.data.remote.HomeRemoteDb
import com.mnowo.composesurveyapp.feature_home.domain.models.SurveyInfo
import com.mnowo.composesurveyapp.feature_home.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val remoteDb: HomeRemoteDb
): HomeRepository {

    override suspend fun getSurveyInfo(): MutableList<SurveyInfo> {
        return remoteDb.getSurveyInfo()
    }

}