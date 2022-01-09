package com.mnowo.composesurveyapp.feature_answer.presentation.answer_screen

import android.os.Parcelable
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class AnswerViewModel @Inject constructor(
    private val getSurveyQuestionsUseCase: GetSurveyQuestionsUseCase,
    private val getCachedQuestionUseCase: GetCachedQuestionUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(AnswerState())
    val state: State<AnswerState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _questionIsSelected = mutableStateOf<Boolean>(false)
    val questionIsSelected: State<Boolean> = _questionIsSelected

    private val _questionList = mutableStateOf<List<GetQuestion>>(emptyList())
    val questionList: State<List<GetQuestion>> = _questionList

    private fun setQuestionIsSelected(value: Boolean) {
        _questionIsSelected.value = value
    }

    private val _title = mutableStateOf<String>("")
    val title: State<String> = _title

    private val _openDialog = mutableStateOf<Boolean>(value = false)
    val openDialog: State<Boolean> = _openDialog

    fun setOpenDialog(value: Boolean) {
        _openDialog.value = value
    }
    
    var currentQuestion = 0
    
    fun setTitle(path: String) {
        _title.value = path
    }

    init {
        viewModelScope.launch {
            delay(1000)
            onEvent(AnswerEvent.GetSurvey)
        }
    }


    @ExperimentalCoroutinesApi
    fun onEvent(answerEvent: AnswerEvent) {
        when (answerEvent) {
            is AnswerEvent.GetSurvey -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                    getSurveyQuestionsUseCase.invoke(title.value).collect {
                        when (it) {
                            is Resource.Success -> {
                                onEvent(AnswerEvent.GetCachedQuestion)
                            }
                            is Resource.Error -> {
                                _state.value = state.value.copy(
                                    isLoading = false
                                )
                                _eventFlow.emit(
                                    UiEvent.ShowSnackbar(
                                        UiText.DynamicString(
                                            "${it.message}"
                                                ?: UiText.unknownError().toString()
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
            is AnswerEvent.GetCachedQuestion -> {
                d("GetSurvey", "GetCachedQuestion")
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                    getCachedQuestionUseCase.invoke().onEach {
                        when (it) {
                            is Resource.Success -> {
                                d("GetSurvey", "ViewModel ${it.data}")
                               it.data?.let { listData ->
                                   _questionList.value = listData
                               }
                                _state.value = state.value.copy(isLoading = false)
                            }
                            is Resource.Error -> {
                                _eventFlow.emit(
                                    UiEvent.ShowSnackbar(UiText.DynamicString(it.message.toString()))
                                )
                            }
                            is Resource.Loading -> {
                                _state.value = state.value.copy(isLoading = true)
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }
            is AnswerEvent.NavigateToHome -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.Navigate(Screen.HomeScreen.route)
                    )
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