package com.mnowo.composesurveyapp.feature_statistics.presentation.statistic_screen

import android.util.Log.d
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.util.Screen
import com.mnowo.composesurveyapp.core.util.UiText
import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion
import com.mnowo.composesurveyapp.feature_statistics.domain.use_case.GetSurveysAnswersUseCase
import com.mnowo.composesurveyapp.feature_statistics.presentation.statistic_screen.util.RoundOffDecimals
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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
                if (currentQuestion.value >= 1) {
                    _currentQuestion.value = currentQuestion.value.minus(1)
                }
            }
            StatisticEvent.NextSurvey -> {
                if (currentQuestion.value < surveyListData.value.size - 1) {
                    _currentQuestion.value = currentQuestion.value.plus(1)
                }
            }
            StatisticEvent.BackToMySurveyList -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.Navigate(Screen.MySurveyListScreen.route)
                    )
                }
            }
        }
    }

    fun calculatePercentage(): DoubleArray {
        var percentageArray = DoubleArray(4)

        viewModelScope.launch {
            val item = surveyListData.value[currentQuestion.value]
            var one = 0
            var two = 0
            var three = 0
            var four = 0

            if(!item.questionOne.isNullOrBlank()) {
                one = item.countOne!!
            }

            if(!item.questionTwo.isNullOrBlank()) {
                two = item.countTwo!!
            }

            if(!item.questionThree.isNullOrBlank()) {
                three = item.countThree!!
            }

            if(!item.questionFour.isNullOrBlank()) {
                four = item.countOne!!
                d("Percentage", "four: $four")
            }

            val oneResult = one > 0
            val twoResult = two > 0
            val threeResult = three > 0
            val fourResult = four > 0

            var counter = 0


            if (oneResult) {
                counter += one
            }

            if (twoResult) {
                counter += two
            }

            if (threeResult) {
                counter += three
            }

            if (fourResult) {
                counter += four
            }

            d("Percentage", "counter: $counter")

            if (oneResult) {
                var firstPercentage: Double = one.toDouble() / counter
                firstPercentage = RoundOffDecimals.roundOffDecimal(firstPercentage)!!
                percentageArray[0] = firstPercentage * 100
                d("Percentage", "first: $firstPercentage")
            } else {
                percentageArray[0] = 0.0
            }

            if (twoResult) {
                var secondPercentage: Double = two.toDouble() / counter
                secondPercentage = RoundOffDecimals.roundOffDecimal(secondPercentage)!!
                percentageArray[1] = secondPercentage * 100
                d("Percentage", "sec: $secondPercentage")
            } else {
                percentageArray[1] = 0.0
            }

            if (threeResult) {
                var thirdPercentage = three.toDouble() / counter
                thirdPercentage = RoundOffDecimals.roundOffDecimal(thirdPercentage)!!
                percentageArray[2] = thirdPercentage * 100
                d("Percentage", "thi: $thirdPercentage")
            } else {
                percentageArray[2] = 0.0
            }

            if (fourResult) {
                var fourthPercentage = four.toDouble() / counter
                fourthPercentage = RoundOffDecimals.roundOffDecimal(fourthPercentage)!!
                percentageArray[3] = fourthPercentage + 100
                d("Percentage", "fourth: $fourthPercentage")
            } else {
                percentageArray[3] = 0.0
            }
        }
        return percentageArray
    }

}