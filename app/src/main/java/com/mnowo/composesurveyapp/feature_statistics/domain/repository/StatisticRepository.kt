package com.mnowo.composesurveyapp.feature_statistics.domain.repository

import com.mnowo.composesurveyapp.core.domain.models.SavingOwnSurvey
import com.mnowo.composesurveyapp.core.domain.models.SurveyInfo

interface StatisticRepository {

    suspend fun getOwnSurveys(collectionList: List<SavingOwnSurvey>) : MutableList<SurveyInfo>

    suspend fun getOwnCachedSurveys(): List<SavingOwnSurvey>
}