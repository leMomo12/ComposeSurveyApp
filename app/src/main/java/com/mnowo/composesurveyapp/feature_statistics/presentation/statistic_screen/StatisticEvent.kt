package com.mnowo.composesurveyapp.feature_statistics.presentation.statistic_screen

sealed class StatisticEvent {
    object NextSurvey: StatisticEvent()
    object PreviousSurvey: StatisticEvent()
    object BackToMySurveyList: StatisticEvent()
}
