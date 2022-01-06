package com.mnowo.composesurveyapp.feature_answer.presentation.before_answer_screen

import android.os.Parcelable
import android.util.Log.d
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.util.Constants
import com.mnowo.composesurveyapp.core.util.Screen
import com.mnowo.composesurveyapp.feature_answer.domain.use_case.DeleteCachedQuestions
import com.mnowo.composesurveyapp.feature_home.domain.models.SurveyInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeforeAnswerViewModel @Inject constructor(
    private val deleteCachedQuestions: DeleteCachedQuestions,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _surveyInfo = mutableStateOf<SurveyInfo>(value = SurveyInfo("", "",0, 0,0, "empty"))
    val surveyInfo: State<SurveyInfo> = _surveyInfo

    var surveyDetail: SurveyInfo = SurveyInfo("","",0,0,0,"")

    init {
        viewModelScope.launch {
            deleteCachedQuestions.invoke()
        }
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
        }
    }
}