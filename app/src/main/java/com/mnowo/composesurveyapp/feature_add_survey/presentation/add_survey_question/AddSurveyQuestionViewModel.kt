package com.mnowo.composesurveyapp.feature_add_survey.presentation.add_survey_question

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnowo.composesurveyapp.core.domain.TextFieldState
import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.util.Constants
import com.mnowo.composesurveyapp.core.util.Screen
import com.mnowo.composesurveyapp.core.util.UiText
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyQuestion
import com.mnowo.composesurveyapp.feature_add_survey.domain.use_case.AddQuestionUseCase
import com.mnowo.composesurveyapp.feature_add_survey.domain.use_case.AddSurveyUseCase
import com.mnowo.composesurveyapp.feature_add_survey.domain.use_case.DeleteAllQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSurveyQuestionViewModel @Inject constructor(
    private val addQuestionUseCase: AddQuestionUseCase,
    private val addSurveyUseCase: AddSurveyUseCase,
    private val deleteAllQuestionsUseCase: DeleteAllQuestionsUseCase
) : ViewModel() {

    var questionCount = 0

    private val _questionTitleState = mutableStateOf(TextFieldState())
    val questionTitleState: State<TextFieldState> = _questionTitleState

    private val _questionOneState = mutableStateOf(TextFieldState())
    val questionOneState: State<TextFieldState> = _questionOneState

    private val _questionTwoState = mutableStateOf(TextFieldState())
    val questionTwoState: State<TextFieldState> = _questionTwoState

    private val _questionThreeState = mutableStateOf(TextFieldState())
    val questionThreeState: State<TextFieldState> = _questionThreeState

    private val _questionFourState = mutableStateOf(TextFieldState())
    val questionFourState: State<TextFieldState> = _questionFourState

    private val _titleError = mutableStateOf<Boolean>(false)
    val titleError: State<Boolean> = _titleError

    private val _state = mutableStateOf(AddSurveyQuestionState())
    val state: State<AddSurveyQuestionState> = _state

    private val _uiEnabled = mutableStateOf(true)
    val uiEnabled: State<Boolean> = _uiEnabled

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun setUiEnabled(enabled: Boolean) {
        _uiEnabled.value = enabled
    }

    fun onEvent(event: AddSurveyQuestionEvent) {
        when (event) {
            is AddSurveyQuestionEvent.EnteredQuestionTitle -> {
                    _questionTitleState.value = questionTitleState.value.copy(
                        text = event.title
                    )
            }
            is AddSurveyQuestionEvent.EnteredQuestionOne -> {

                _questionOneState.value = questionOneState.value.copy(
                    text = event.question
                )
            }
            is AddSurveyQuestionEvent.EnteredQuestionTwo -> {
                _questionTwoState.value = questionTwoState.value.copy(
                    text = event.question
                )
            }
            is AddSurveyQuestionEvent.EnteredQuestionThree -> {
                _questionThreeState.value = questionThreeState.value.copy(
                    text = event.question
                )
            }
            is AddSurveyQuestionEvent.EnteredQuestionFour -> {
                _questionFourState.value = questionFourState.value.copy(
                    text = event.question
                )
            }
            is AddSurveyQuestionEvent.AddQuestion -> {
                Log.d("AddQuestion", "In viewModel")
                viewModelScope.launch {
                    _state.value = state.value.copy(isLoading = true)
                    addQuestionUseCase(
                        surveyQuestion = SurveyQuestion(
                            0,
                            questionTitleState.value.text,
                            questionOneState.value.text,
                            questionTwoState.value.text,
                            questionThreeState.value.text,
                            questionFourState.value.text
                        )
                    ).onEach { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _state.value = AddSurveyQuestionState(isLoading = true)
                            }
                            is Resource.Success -> {
                                _state.value = AddSurveyQuestionState(isLoading = false)
                                _eventFlow.emit(
                                    UiEvent.ShowSnackbar(UiText.DynamicString("Successfully added question"))
                                )
                                blankAfterSuccessfullyAddingQuestion()
                            }
                            is Resource.Error -> {
                                _state.value = AddSurveyQuestionState(isLoading = false)
                                _eventFlow.emit(
                                    UiEvent.ShowSnackbar(
                                        UiText.DynamicString(
                                            result.message ?: "Failed to add question"
                                        )
                                    )
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }
            is AddSurveyQuestionEvent.PublishSurvey -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(isLoading = true)
                    addSurveyUseCase().onEach { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _state.value = AddSurveyQuestionState(isLoading = true)
                            }
                            is Resource.Success -> {
                                _state.value = AddSurveyQuestionState(isLoading = false)

                                deleteAllQuestionsUseCase.invoke().launchIn(viewModelScope)

                                _eventFlow.emit(
                                    UiEvent.Navigate(Screen.DoneScreen.route)
                                )
                            }
                            is Resource.Error -> {
                                _state.value = AddSurveyQuestionState(isLoading = false)
                                _eventFlow.emit(
                                    UiEvent.ShowSnackbar(
                                        UiText.DynamicString(
                                            "${result.message}, you may forgot to add a question first" ?: "Unexpected error occurred, you may forgot to add a question first"
                                        )
                                    )
                                )
                            }
                        }

                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    private fun blankAfterSuccessfullyAddingQuestion() = viewModelScope.launch {
        questionCount++
        _questionTitleState.value = questionTitleState.value.copy(
            text = ""
        )
        _questionOneState.value = questionOneState.value.copy(
            text = ""
        )
        _questionTwoState.value = questionTwoState.value.copy(
            text = ""
        )
        _questionThreeState.value = questionThreeState.value.copy(
            text = ""
        )
        _questionFourState.value = questionFourState.value.copy(
            text = ""
        )
    }
}
