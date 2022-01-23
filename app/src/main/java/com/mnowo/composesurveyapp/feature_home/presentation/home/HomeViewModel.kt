package com.mnowo.composesurveyapp.feature_home.presentation.home

import android.util.Log.d
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.util.Screen
import com.mnowo.composesurveyapp.core.util.UiText
import com.mnowo.composesurveyapp.feature_auth.presentation.register.RegisterState
import com.mnowo.composesurveyapp.feature_home.domain.models.SurveyInfo
import com.mnowo.composesurveyapp.feature_home.domain.repository.HomeRepository
import com.mnowo.composesurveyapp.feature_home.domain.use_case.GetSurveyInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSurveyInfoUseCase: GetSurveyInfoUseCase
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _surveyInfoState = mutableStateOf<SurveyInfo>(SurveyInfo("", "", 0, 0, 0, "empty"))
    val surveyInfoState: State<SurveyInfo> = _surveyInfoState

    private val _surveyInfoList = mutableStateOf<MutableList<SurveyInfo>>(mutableListOf())
    val surveyInfoList: State<MutableList<SurveyInfo>> = _surveyInfoList

    fun setSurveyInfoState(value: SurveyInfo) {
        _surveyInfoState.value = value
    }

    init {
        viewModelScope.launch {
            getSurveyInfoUseCase.invoke().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            _surveyInfoList.value = it
                        }
                        _surveyInfoList.value.shuffle()
                        _state.value = state.value.copy(
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(UiText.DynamicString(result.message.toString()))
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.AddNewSurvey -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.Navigate(Screen.NewSurveyScreen.route)
                    )
                }
            }
            is HomeEvent.NavigateToBeforeSurvey -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.Navigate(Screen.BeforeAnswerScreen.route)
                    )
                }
            }
        }
    }
}