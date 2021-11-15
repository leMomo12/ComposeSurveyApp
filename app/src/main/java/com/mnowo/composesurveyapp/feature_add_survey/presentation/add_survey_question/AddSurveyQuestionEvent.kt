package com.mnowo.composesurveyapp.feature_add_survey.presentation.add_survey_question

sealed class AddSurveyQuestionEvent {
    data class EnteredQuestionTitle(var title: String) : AddSurveyQuestionEvent()
    data class EnteredQuestionOne(var question: String) : AddSurveyQuestionEvent()
    data class EnteredQuestionTwo(var question: String) : AddSurveyQuestionEvent()
    data class EnteredQuestionThree(var question: String) : AddSurveyQuestionEvent()
    data class EnteredQuestionFour(var question: String) : AddSurveyQuestionEvent()
    object AddQuestion : AddSurveyQuestionEvent()
    object PublishSurvey : AddSurveyQuestionEvent()
}
