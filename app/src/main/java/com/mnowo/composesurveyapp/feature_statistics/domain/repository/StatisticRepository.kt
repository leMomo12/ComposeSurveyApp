package com.mnowo.composesurveyapp.feature_statistics.domain.repository

import com.mnowo.composesurveyapp.core.domain.models.SavingOwnSurvey
import com.mnowo.composesurveyapp.core.domain.models.SurveyInfo
import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion

interface StatisticRepository {

    suspend fun getOwnSurveys(collectionList: List<SavingOwnSurvey>) : MutableList<SurveyInfo>

    suspend fun getOwnCachedSurveys(): List<SavingOwnSurvey>

    suspend fun getAnswer(collectionPath: String) : List<GetQuestion>
}