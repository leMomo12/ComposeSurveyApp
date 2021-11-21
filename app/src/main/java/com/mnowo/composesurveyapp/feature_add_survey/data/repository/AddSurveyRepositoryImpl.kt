package com.mnowo.composesurveyapp.feature_add_survey.data.repository

import android.util.Log.d
import com.mnowo.composesurveyapp.feature_add_survey.data.local.AddSurveyDao
import com.mnowo.composesurveyapp.feature_add_survey.data.remote.AddSurveyRemoteDb
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyQuestion
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyTitleAndDescription
import com.mnowo.composesurveyapp.feature_add_survey.domain.repositrory.AddSurveyRepository
import javax.inject.Inject

class AddSurveyRepositoryImpl @Inject constructor(
    private val addSurveyDao: AddSurveyDao,
    private val addSurveyRemoteDb: AddSurveyRemoteDb
) : AddSurveyRepository {

    override suspend fun addQuestion(surveyQuestion: SurveyQuestion) {
        return addSurveyDao.addQuestion(surveyQuestion)
    }

    override suspend fun getSurveyQuestions(): List<SurveyQuestion> {
        return addSurveyDao.getSurveyQuestions()
    }

    override suspend fun getTitleAndDescription(): SurveyTitleAndDescription {
        return addSurveyDao.getTitleAndDescription()
    }

    override suspend fun addSurvey(
        surveyQuestion: SurveyQuestion,
        surveyTitleAndDescription: SurveyTitleAndDescription
    ) {
        return addSurveyRemoteDb.addSurveyQuestions(surveyQuestion, surveyTitleAndDescription)
    }

    override suspend fun addSurveyTitleAndDescription(surveyTitleAndDescription: SurveyTitleAndDescription) {
        d("Add", "Here")
        return addSurveyDao.addSurveyTitleAndDescription(surveyTitleAndDescription)
    }

}