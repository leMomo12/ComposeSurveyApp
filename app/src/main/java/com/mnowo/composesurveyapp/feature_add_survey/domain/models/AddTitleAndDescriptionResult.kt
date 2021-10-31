package com.mnowo.composesurveyapp.feature_add_survey.domain.models

data class AddTitleAndDescriptionResult(
    var titleError: Boolean? = null,
    var descriptionError: Boolean? = null
)
