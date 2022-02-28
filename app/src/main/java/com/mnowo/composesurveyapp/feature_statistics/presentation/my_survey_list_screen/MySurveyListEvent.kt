package com.mnowo.composesurveyapp.feature_statistics.presentation.my_survey_list_screen

sealed class MySurveyListEvent {
    object NavBackToHome : MySurveyListEvent()
    data class NavToStatistics(var title: String)  : MySurveyListEvent()
    object More : MySurveyListEvent()
}
