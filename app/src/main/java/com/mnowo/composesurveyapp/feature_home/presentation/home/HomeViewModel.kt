package com.mnowo.composesurveyapp.feature_home.presentation.home

import android.util.Log.d
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.util.Screen
import com.mnowo.composesurveyapp.feature_auth.presentation.register.RegisterState
import com.mnowo.composesurveyapp.feature_home.domain.models.SurveyInfo
import com.mnowo.composesurveyapp.feature_home.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _surveyInfoState = mutableStateOf<SurveyInfo>(SurveyInfo("", "", 0, 0, 0, "empty"))
    val surveyInfoState: State<SurveyInfo> = _surveyInfoState

    private val _surveyInfoList = mutableStateOf<MutableList<SurveyInfo>>(mutableListOf())
    val surveyInfoList: State<MutableList<SurveyInfo>> = _surveyInfoList

    init {
        viewModelScope.launch {
            _surveyInfoList.value = homeRepository.getSurveyInfo()
        }
        _surveyInfoList.value.shuffle()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.SurveyInfoState -> {
                _surveyInfoState.value = SurveyInfo(
                    event.data.title,
                    event.data.description,
                    event.data.questionCount,
                    event.data.likes,
                    event.data.dislikes,
                    event.data.errorMessage
                )
            }

            is HomeEvent.AddNewSurvey -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.Navigate(Screen.NewSurveyScreen.route)
                    )
                }
            }
            is HomeEvent.NavigateToBeforeSurvey -> {
                viewModelScope.launch {
                    d("savedState", "$surveyInfoState")
                    _eventFlow.emit(
                        UiEvent.Navigate(Screen.BeforeAnswerScreen.route)
                    )
                }
            }
        }
    }
}