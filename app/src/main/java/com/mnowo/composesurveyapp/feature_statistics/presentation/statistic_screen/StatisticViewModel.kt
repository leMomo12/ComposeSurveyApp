package com.mnowo.composesurveyapp.feature_statistics.presentation.statistic_screen

import androidx.lifecycle.ViewModel
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.feature_statistics.domain.use_case.GetSurveysAnswersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val getSurveysAnswersUseCase: GetSurveysAnswersUseCase
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getSurveysAnswersUseCase.invoke()
    }

}