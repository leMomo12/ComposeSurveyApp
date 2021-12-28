package com.mnowo.composesurveyapp.feature_answer.domain.models

data class GetQuestion(
    var id: Int,
    var questionTitle: String,
    var questionOne: String?,
    var questionTwo: String?,
    var questionThree: String?,
    var questionFour: String?,
    var error: String? = null
)
