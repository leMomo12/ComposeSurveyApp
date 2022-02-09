package com.mnowo.composesurveyapp.feature_home.presentation.home

import com.mnowo.composesurveyapp.feature_home.domain.models.SurveyInfo

sealed class HomeEvent {
    object AddNewSurvey : HomeEvent()
    object NavToStatistics : HomeEvent()
    object Logout : HomeEvent()
    data class NavigateToBeforeSurvey(var data: SurveyInfo) : HomeEvent()
}