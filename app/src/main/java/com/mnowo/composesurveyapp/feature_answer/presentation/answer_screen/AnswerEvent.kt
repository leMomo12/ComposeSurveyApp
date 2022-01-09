package com.mnowo.composesurveyapp.feature_answer.presentation.answer_screen

sealed class AnswerEvent {
    object GetSurvey : AnswerEvent()
    object GetCachedQuestion : AnswerEvent()
    object NavigateToHome : AnswerEvent()
}
