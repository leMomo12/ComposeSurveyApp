package com.mnowo.composesurveyapp.feature_home.presentation.home

sealed class HomeEvent {
    object AddNewSurvey : HomeEvent()
    object ScrollUp : HomeEvent()
}