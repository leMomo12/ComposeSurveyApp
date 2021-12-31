package com.mnowo.composesurveyapp.feature_answer.presentation.answer_screen

import android.util.Log.d
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.ui.theme.blue
import com.mnowo.composesurveyapp.core.util.Constants
import com.mnowo.composesurveyapp.feature_answer.domain.use_case.GetSurveyQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnswerViewModel @Inject constructor(
    private val getSurveyQuestionsUseCase: GetSurveyQuestionsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(AnswerState())
    val state: State<AnswerState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _questionIsSelected = mutableStateOf<Boolean>(false)
    val questionIsSelected: State<Boolean> = _questionIsSelected

    fun setQuestionIsSelected(value: Boolean) {
        _questionIsSelected.value = value
    }

    var title: String? = ""

    init {
        // Gets surveyPath from arguments
        savedStateHandle.get<String>(Constants.PARAM_SURVEY_PATH)?.let { surveyPath ->
            title = surveyPath
            d("savedState", "Title: $title")
        }
    }

    fun onEvent(answerEvent: AnswerEvent) {
        when (answerEvent) {

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