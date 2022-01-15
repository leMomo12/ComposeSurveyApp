package com.mnowo.composesurveyapp.feature_answer.presentation.answer_screen

sealed class AnswerEvent {
    object ProgressIndicator : AnswerEvent()
    object NextQuestion : AnswerEvent()
    object GetSurvey : AnswerEvent()
    object GetCachedQuestion : AnswerEvent()
    object NavigateToHome : AnswerEvent()
}
