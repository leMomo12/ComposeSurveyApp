package com.mnowo.composesurveyapp.feature_add_survey.domain.models

data class AnswerCounter(
    var id: Int,
    var firstQuestion: Int = 0,
    var secondQuestion: Int = 0,
    var thirdQuestion: Int = 0,
    var fourthQuestion: Int = 0
)
