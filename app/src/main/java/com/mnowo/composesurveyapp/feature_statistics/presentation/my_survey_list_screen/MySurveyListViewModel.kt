package com.mnowo.composesurveyapp.feature_statistics.presentation.my_survey_list_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnowo.composesurveyapp.core.domain.models.SurveyInfo
import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.util.Screen
import com.mnowo.composesurveyapp.core.util.UiText
import com.mnowo.composesurveyapp.feature_statistics.domain.use_case.GetOwnSurveysUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MySurveyListViewModel @Inject constructor(
    private val getOwnSurveysUseCase: GetOwnSurveysUseCase
) : ViewModel() {

    private val _state = mutableStateOf(MySurveyListState())
    val state: State<MySurveyListState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _surveyInfoList = mutableStateOf<MutableList<SurveyInfo>>(mutableListOf())
    val surveyInfoList: State<MutableList<SurveyInfo>> = _surveyInfoList

    init {
        getOwnSurveysUseCase.invoke().onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = state.value.copy(isLoading = false)
                    it.data?.let { listData ->
                        _surveyInfoList.value = listData
                    }
                }
                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            UiText.DynamicString(
                                it.message ?: UiText.unknownError().toString()
                            )
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: MySurveyListEvent) {
        when (event) {
            is MySurveyListEvent.More -> {}
            is MySurveyListEvent.NavBackToHome -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.Navigate(Screen.HomeScreen.route)
                    )
                }
            }
            is MySurveyListEvent.NavToStatistics -> {}
        }
    }
}