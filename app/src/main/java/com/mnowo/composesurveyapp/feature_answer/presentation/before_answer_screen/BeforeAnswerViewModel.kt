package com.mnowo.composesurveyapp.feature_answer.presentation.before_answer_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.util.Screen
import com.mnowo.composesurveyapp.feature_answer.domain.use_case.DeleteCachedAnswerUseCase
import com.mnowo.composesurveyapp.feature_answer.domain.use_case.DeleteCachedQuestionsUseCase
import com.mnowo.composesurveyapp.core.domain.models.SurveyInfo
import com.mnowo.composesurveyapp.core.util.RoundOffDecimals
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeforeAnswerViewModel @Inject constructor(
    private val deleteCachedQuestionsUseCase: DeleteCachedQuestionsUseCase,
    private val deleteCachedAnswerUseCase: DeleteCachedAnswerUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _surveyInfo = mutableStateOf<SurveyInfo>(value = SurveyInfo("", "",0, 0,0, "empty"))
    val surveyInfo: State<SurveyInfo> = _surveyInfo

    var surveyDetail: SurveyInfo = SurveyInfo("","",0,0,0,"")

    private val _minExpectedTime = mutableStateOf<Double>(0.0)
    val minExpectedTime: State<Double> = _minExpectedTime

    private val _maxExpectedTime = mutableStateOf<Double>(0.0)
    val maxExpectedTime: State<Double> = _maxExpectedTime

    fun setMinExpectedTime(questionCount: Int) {
        _minExpectedTime.value = calcMinimumTime(questionCount = questionCount)
    }

    fun setMaxExpectedTime(questionCount: Int) {
        _maxExpectedTime.value = calcMaximumTime(questionCount = questionCount)
    }

    fun onEvent(beforeAnswerEvent: BeforeAnswerEvent) {
        when (beforeAnswerEvent) {
            is BeforeAnswerEvent.NavigateToAnswer -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.Navigate(Screen.AnswerScreen.route)
                    )
                }
            }
            is BeforeAnswerEvent.DeleteAllCachedQuestions -> {
                viewModelScope.launch {
                    deleteCachedQuestionsUseCase.invoke()
                }
            }
            is BeforeAnswerEvent.DeleteAllCachedAnswers -> {
                viewModelScope.launch {
                    deleteCachedAnswerUseCase.invoke()
                }
            }
        }
    }

    fun calcMinimumTime(questionCount: Int): Double {
        var expectedTime = 0.0
        viewModelScope.launch {
            expectedTime = questionCount * 0.25
            expectedTime = RoundOffDecimals.roundOffDecimal(expectedTime)!!
        }
        return expectedTime
    }

    fun calcMaximumTime(questionCount: Int): Double {
        var expectedTime = 0.0
        viewModelScope.launch {
            expectedTime = questionCount * 0.5
            expectedTime = RoundOffDecimals.roundOffDecimal(expectedTime)!!
        }
        return expectedTime
    }
}