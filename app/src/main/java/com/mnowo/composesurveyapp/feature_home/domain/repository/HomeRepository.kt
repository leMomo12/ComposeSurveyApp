package com.mnowo.composesurveyapp.feature_home.domain.repository

import com.mnowo.composesurveyapp.feature_home.data.repository.HomeRepositoryImpl
import com.mnowo.composesurveyapp.feature_home.domain.models.SurveyInfo

interface HomeRepository {

    suspend fun getSurveyInfo(): MutableList<SurveyInfo>
}