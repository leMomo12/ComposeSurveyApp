package com.mnowo.composesurveyapp.feature_add_survey.domain.repositrory

import com.mnowo.composesurveyapp.core.domain.models.RemoteDbRespond
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyQuestion
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyTitleAndDescription

interface AddSurveyRepository {

    suspend fun addQuestion(surveyQuestion: SurveyQuestion)

    suspend fun getSurveyQuestions(): List<SurveyQuestion>

    suspend fun getTitleAndDescription(): SurveyTitleAndDescription

    suspend fun addSurvey(
        surveyQuestion: SurveyQuestion,
        surveyTitleAndDescription: SurveyTitleAndDescription
    ): RemoteDbRespond

    suspend fun addSurveyTitleAndDescription(surveyTitleAndDescription: SurveyTitleAndDescription): RemoteDbRespond

    suspend fun addSurveyTitleAndDescriptionToRoom(surveyTitleAndDescription: SurveyTitleAndDescription)

    suspend fun deleteAllQuestions()

    suspend fun addSurveyTitleAndDescriptionToInfo(surveyTitleAndDescription: SurveyTitleAndDescription): RemoteDbRespond
}