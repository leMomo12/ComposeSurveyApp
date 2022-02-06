package com.mnowo.composesurveyapp.feature_answer.presentation.after_answer_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnowo.composesurveyapp.core.domain.TextFieldState
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.util.Screen
import com.mnowo.composesurveyapp.feature_answer.domain.models.Answer
import com.mnowo.composesurveyapp.feature_answer.domain.repository.AnswerRepository
import com.mnowo.composesurveyapp.feature_answer.domain.use_case.SurveyRatingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AfterAnswerViewModel @Inject constructor(
    private val surveyRatingUseCase: SurveyRatingUseCase
) : ViewModel() {

    private val _title = mutableStateOf(TextFieldState())
    val title: State<TextFieldState> = _title

    fun setTitle(value: TextFieldState) {
        _title.value = value
    }

    private val _thumbUp = mutableStateOf<Boolean>(false)
    val thumbUp: State<Boolean> = _thumbUp

    fun setThumbUp(value: Boolean) {
        _thumbUp.value = value
    }

    private val _thumbDown = mutableStateOf<Boolean>(false)
    val thumbDown: State<Boolean> = _thumbDown

    fun setThumbDown(value: Boolean) {
        _thumbDown.value = value
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun navigateToHome() {
        viewModelScope.launch {
            when {
                thumbUp.value == true -> {
                    surveyRatingUseCase.invoke(thumbUp = true, surveyName = title.value.text)
                        .launchIn(viewModelScope)

                    _eventFlow.emit(
                        UiEvent.Navigate(Screen.HomeScreen.route)
                    )
                }
                thumbDown.value == true -> {
                    surveyRatingUseCase.invoke(thumbUp = false, surveyName = title.value.text)
                        .launchIn(viewModelScope)

                    _eventFlow.emit(
                        UiEvent.Navigate(Screen.HomeScreen.route)
                    )
                }
                else -> {
                    _eventFlow.emit(
                        UiEvent.Navigate(Screen.HomeScreen.route)
                    )
                }
            }
        }
    }


}