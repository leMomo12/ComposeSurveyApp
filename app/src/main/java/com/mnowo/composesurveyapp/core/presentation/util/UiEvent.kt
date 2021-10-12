package com.mnowo.composesurveyapp.core.presentation.util

import com.mnowo.composesurveyapp.core.util.UiText

sealed class UiEvent {

    data class ShowSnackbar(val uiText: UiText) : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    object NavigateUp : UiEvent()

}
