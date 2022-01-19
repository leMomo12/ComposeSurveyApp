package com.mnowo.composesurveyapp.feature_answer.presentation.before_answer_screen

sealed class BeforeAnswerEvent {
    object DeleteAllCachedQuestions : BeforeAnswerEvent()
    object DeleteAllCachedAnswers : BeforeAnswerEvent()
    object NavigateToAnswer : BeforeAnswerEvent()
}
