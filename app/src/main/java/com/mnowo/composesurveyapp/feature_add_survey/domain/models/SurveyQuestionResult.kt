package com.mnowo.composesurveyapp.feature_add_survey.domain.models

data class SurveyQuestionResult(
    var titleError: Boolean? = null,
    var lessThanTwoFilledAnswerOptions: Boolean? = null
)
