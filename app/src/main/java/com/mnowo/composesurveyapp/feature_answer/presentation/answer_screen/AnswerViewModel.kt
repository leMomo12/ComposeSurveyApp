package com.mnowo.composesurveyapp.feature_answer.presentation.answer_screen

import android.util.Log.d
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.ui.theme.blue
import com.mnowo.composesurveyapp.core.util.Constants
import com.mnowo.composesurveyapp.core.util.Screen
import com.mnowo.composesurveyapp.core.util.UiText
import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion
import com.mnowo.composesurveyapp.feature_answer.domain.use_case.GetCachedQuestionUseCase
import com.mnowo.composesurveyapp.feature_answer.domain.use_case.GetSurveyQuestionsUseCase
import com.mnowo.composesurveyapp.feature_home.domain.models.SurveyInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnswerViewModel @Inject constructor(
    private val getSurveyQuestionsUseCase: GetSurveyQuestionsUseCase,
    private val getCachedQuestionUseCase: GetCachedQuestionUseCase
) : ViewModel() {

    private val _state = mutableStateOf(AnswerState())
    val state: State<AnswerState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _questionIsSelected = mutableStateOf<Boolean>(false)
    val questionIsSelected: State<Boolean> = _questionIsSelected

    private val _surveyData = mutableStateOf<List<GetQuestion>>(value = emptyList())
    val surveyData: State<List<GetQuestion>> = _surveyData

    var currentQuestion = 0

    fun setQuestionIsSelected(value: Boolean) {
        _questionIsSelected.value = value
    }


    var surveyDetail: SurveyInfo? = SurveyInfo("","",0,0,0,"")


    fun onEvent(answerEvent: AnswerEvent) {
        when (answerEvent) {
            is AnswerEvent.GetSurvey -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                    getSurveyQuestionsUseCase.invoke(surveyDetail?.title.toString()).collect {
                        when (it) {
                            is Resource.Success -> {
                                _surveyData.value = it.data!!
                                _state.value = state.value.copy(
                                    isLoading = false
                                )
                                d("GetQuestion", "${_surveyData.value.toString()}")
                            }
                            is Resource.Error -> {
                                _state.value = state.value.copy(
                                    isLoading = false
                                )
                                _eventFlow.emit(
                                    UiEvent.ShowSnackbar(
                                        UiText.DynamicString(
                                            "${it.message}"
                                                ?: "Unexpected error occurred"
                                        )
                                    )
                                )
                                _eventFlow.emit(
                                    UiEvent.Navigate(Screen.HomeScreen.route)
                                )
                            }
                            is Resource.Loading -> {
                                _state.value = state.value.copy(
                                    isLoading = true
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun checkColor(oldColor: Color): Color {
        var color = oldColor

        viewModelScope.launch {
            if (oldColor == blue) {
                color = Color.LightGray
                setQuestionIsSelected(false)
                d("colorState", "is blue")
            } else if (oldColor == Color.LightGray && !questionIsSelected.value) {
                color = blue
                setQuestionIsSelected(true)
                d("colorState", "is grey")
            }
        }
        return color
    }
}