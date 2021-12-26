package com.mnowo.composesurveyapp.feature_answer.presentation.before_answer_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeforeAnswerViewModel @Inject constructor(

) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(beforeAnswerEvent: BeforeAnswerEvent) {
        when (beforeAnswerEvent) {
            is BeforeAnswerEvent.NavigateToAnswer -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.Navigate(Screen.AnswerScreen.route)
                    )
                }
            }
        }
    }
}