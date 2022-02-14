package com.mnowo.composesurveyapp.feature_add_survey.data.repository

import com.mnowo.composesurveyapp.core.domain.models.RemoteDbRespond
import com.mnowo.composesurveyapp.core.domain.models.SavingOwnSurvey
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
    ): RemoteDbRespond {
        return addSurveyRemoteDb.addSurveyQuestions(surveyQuestion, surveyTitleAndDescription)
    }

    override suspend fun savingOwnSurvey(savingOwnSurvey: SavingOwnSurvey) {
        return addSurveyDao.savingOwnSurvey(savingOwnSurvey = savingOwnSurvey)
    }

    override suspend fun addSurveyTitleAndDescription(surveyTitleAndDescription: SurveyTitleAndDescription): RemoteDbRespond {
        return addSurveyRemoteDb.addSurveyTitleAndDescription(surveyTitleAndDescription)
    }

    override suspend fun addSurveyTitleAndDescriptionToRoom(surveyTitleAndDescription: SurveyTitleAndDescription) {
        return addSurveyDao.addSurveyTitleAndDescriptionToRoom(surveyTitleAndDescription)
    }

    override suspend fun deleteAllQuestions() {
        return addSurveyDao.deleteAllQuestions()
    }

    override suspend fun addSurveyTitleAndDescriptionToInfo(
        surveyTitleAndDescription: SurveyTitleAndDescription,
        questionCount: Int
    ): RemoteDbRespond {
        return addSurveyRemoteDb.addSurveyTitleAndDescriptionToInfo(surveyTitleAndDescription, questionCount)
    }

}