package com.mnowo.composesurveyapp.feature_statistics.presentation.statistic_screen

import android.util.Log.d
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.util.UiText
import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion
import com.mnowo.composesurveyapp.feature_statistics.domain.use_case.GetSurveysAnswersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val getSurveysAnswersUseCase: GetSurveysAnswersUseCase
) : ViewModel() {

    private val _state = mutableStateOf(StatisticState())
    val state: State<StatisticState> = _state


    private val _surveyPath = mutableStateOf<String>("")
    val surveyPath: State<String> = _surveyPath

    fun setSurveyPath(value: String) {
        _surveyPath.value = value
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _surveyListData = mutableStateOf<List<GetQuestion>>(emptyList())
    val surveyListData: State<List<GetQuestion>> = _surveyListData


    private val _currentQuestion = mutableStateOf<Int>(0)
    val currentQuestion: State<Int> = _currentQuestion

    fun setCurrentQuestion(value: Int) {
        _currentQuestion.value = value
    }

    fun getSurveyAnswers() {
        d("stats", "start")
        getSurveysAnswersUseCase.invoke(surveyPath.value).onEach {
            when (it) {
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            UiText.DynamicString(it.message ?: UiText.unknownError().toString())
                        )
                    )
                }
                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    d("stats", "finished")
                    it.data?.let { data ->
                        _surveyListData.value = data
                    }
                    _state.value = state.value.copy(isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: StatisticEvent) {
        when (event) {
            StatisticEvent.PreviousSurvey -> {
                if(currentQuestion.value >= 1) {
                    _currentQuestion.value = currentQuestion.value.minus(1)
                }
            }
            StatisticEvent.NextSurvey -> {
                if(currentQuestion.value < surveyListData.value.size - 1) {
                    _currentQuestion.value = currentQuestion.value.plus(1)
                }
            }
        }
    }

}