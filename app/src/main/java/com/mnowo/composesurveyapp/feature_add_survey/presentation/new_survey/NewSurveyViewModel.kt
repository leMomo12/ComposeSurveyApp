package com.mnowo.composesurveyapp.feature_add_survey.presentation.new_survey

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnowo.composesurveyapp.core.domain.TextFieldState
import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.util.Screen
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyTitleAndDescription
import com.mnowo.composesurveyapp.feature_add_survey.domain.use_case.AddSurveyTitleAndDescriptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewSurveyViewModel @Inject constructor(
    private val addSurveyTitleAndDescriptionUseCase: AddSurveyTitleAndDescriptionUseCase
) : ViewModel() {

    private val _titleState = mutableStateOf(TextFieldState())
    val titleState: State<TextFieldState> = _titleState

    private val _descriptionState = mutableStateOf(TextFieldState())
    val descriptionState: State<TextFieldState> = _descriptionState

    private val _isTitleError = mutableStateOf(false)
    val isTitleError: State<Boolean> = _isTitleError

    private val _isDescriptionError = mutableStateOf<Boolean>(false)
    val isDescriptionError: State<Boolean> = _isDescriptionError

    private val _state = mutableStateOf(NewSurveyState())
    val state: State<NewSurveyState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: NewSurveyEvent) {
        when (event) {
            is NewSurveyEvent.EnteredTitle -> {
                _titleState.value = titleState.value.copy(
                    text = event.title
                )
            }
            is NewSurveyEvent.EnteredDescription -> {
                _descriptionState.value = descriptionState.value.copy(
                    text = event.description
                )
            }
            is NewSurveyEvent.AddTitleAndDescription -> {
                _isTitleError.value = false
                _isDescriptionError.value = false

                viewModelScope.launch {
                    _state.value = state.value.copy(isLoading = true)
                    addSurveyTitleAndDescriptionUseCase(
                        SurveyTitleAndDescription(
                            1,
                            titleState.value.text,
                            descriptionState.value.text
                        )
                    ).onEach { result ->
                        when(result) {
                            is Resource.Loading -> {
                                _state.value = NewSurveyState(isLoading = true)
                            }
                            is Resource.Success -> {
                                _state.value = NewSurveyState(isLoading = false)
                                _eventFlow.emit(
                                    UiEvent.Navigate(Screen.AddSurveyQuestionScreen.route)
                                )
                            }
                            is Resource.Error -> {
                                when {
                                    result.data?.titleError != null -> {
                                        _isTitleError.value = true
                                    }
                                    result.data?.descriptionError != null -> {
                                        _isDescriptionError.value = true
                                    }
                                }
                                _state.value = NewSurveyState(isLoading = false)
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }

        }
    }
}