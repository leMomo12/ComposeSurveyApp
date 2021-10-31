package com.mnowo.composesurveyapp.feature_add_survey.presentation.new_survey

sealed class NewSurveyEvent {
    data class EnteredTitle(val title: String) : NewSurveyEvent()
    data class EnteredDescription(val description: String) : NewSurveyEvent()
    object AddTitleAndDescription : NewSurveyEvent()
}

