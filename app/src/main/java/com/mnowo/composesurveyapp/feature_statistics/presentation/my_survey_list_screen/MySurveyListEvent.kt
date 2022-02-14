package com.mnowo.composesurveyapp.feature_statistics.presentation.my_survey_list_screen

sealed class MySurveyListEvent {
    object NavBackToHome : MySurveyListEvent()
    object NavToStatistics  : MySurveyListEvent()
    object More : MySurveyListEvent()
}
