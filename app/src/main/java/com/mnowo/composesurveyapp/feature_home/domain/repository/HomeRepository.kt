package com.mnowo.composesurveyapp.feature_home.domain.repository

import com.mnowo.composesurveyapp.core.domain.models.SurveyInfo

interface HomeRepository {

    suspend fun getSurveyInfo(): MutableList<SurveyInfo>
}