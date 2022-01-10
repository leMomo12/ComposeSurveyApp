package com.mnowo.composesurveyapp.feature_answer.presentation.answer_screen

sealed class AnswerEvent {
    data class ProgressIndicator(var value: Float) : AnswerEvent()
    object GetSurvey : AnswerEvent()
    object GetCachedQuestion : AnswerEvent()
    object NavigateToHome : AnswerEvent()
}
