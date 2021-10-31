package com.mnowo.composesurveyapp.feature_add_survey.domain.repositrory

import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyTitleAndDescription

interface AddSurveyRepository {

    suspend fun addSurveyTitleAndDescription(surveyTitleAndDescription: SurveyTitleAndDescription)
}