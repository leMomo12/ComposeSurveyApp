package com.mnowo.composesurveyapp.feature_add_survey.data.repository

import com.mnowo.composesurveyapp.core.models.RemoteDbRespond
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyQuestion
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyTitleAndDescription
import com.mnowo.composesurveyapp.feature_add_survey.domain.repositrory.AddSurveyRepository
import org.junit.Assert.*

class FakeAddSurveyRepositoryTest : AddSurveyRepository {

    private var questionList = mutableListOf<SurveyQuestion>()


    override suspend fun addQuestion(surveyQuestion: SurveyQuestion) {
        questionList.add(SurveyQuestion(1, "title", "one", "two", "three", "four"))
    }

    override suspend fun getSurveyQuestions(): List<SurveyQuestion> {
        return questionList
    }

    override suspend fun getTitleAndDescription(): SurveyTitleAndDescription {
        return SurveyTitleAndDescription(1, "title", "description")
    }

    override suspend fun addSurvey(
        surveyQuestion: SurveyQuestion,
        surveyTitleAndDescription: SurveyTitleAndDescription
    ): RemoteDbRespond {
        return RemoteDbRespond(isSuccessful = true)
    }

    override suspend fun addSurveyTitleAndDescription(surveyTitleAndDescription: SurveyTitleAndDescription): RemoteDbRespond {
        TODO()
    }

    override suspend fun addSurveyTitleAndDescriptionToRoom(surveyTitleAndDescription: SurveyTitleAndDescription) {

    }

    override suspend fun deleteAllQuestions() {
        questionList.clear()
    }

}