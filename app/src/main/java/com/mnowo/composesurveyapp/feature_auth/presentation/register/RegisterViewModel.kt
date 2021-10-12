package com.mnowo.composesurveyapp.feature_auth.presentation.register

import android.util.Log.d
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnowo.composesurveyapp.core.domain.TextFieldState
import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.util.Screen
import com.mnowo.composesurveyapp.core.util.UiText
import com.mnowo.composesurveyapp.feature_auth.domain.use_case.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _passwordVisibility = mutableStateOf(false)
    val passwordVisibility: State<Boolean> = _passwordVisibility

    private val _uiEnable = mutableStateOf(true)
    val uiEnable: State<Boolean> = _uiEnable

    private val _isErrorPassword = mutableStateOf(false)
    val isErrorPassword: State<Boolean> = _isErrorPassword

    private val _isErrorEmail = mutableStateOf(false)
    val isErrorEmail: State<Boolean> = _isErrorEmail

    private val _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState

    private val _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState

    private val _state = mutableStateOf(RegisterState())
    val state: State<RegisterState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun setUiEnable(status: Boolean) {
        _uiEnable.value = status
    }

    fun setPasswordVisibility(isVisible: Boolean) {
        _passwordVisibility.value = isVisible
    }

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EnteredEmail -> {
                _emailState.value = emailState.value.copy(
                    event.email
                )
            }
            is RegisterEvent.EnteredPassword -> {
                _passwordState.value = passwordState.value.copy(
                    event.password
                )
            }
            is RegisterEvent.Register -> {
                d("Registration", "RegisterEvent")
                viewModelScope.launch {
                    _state.value = state.value.copy(isLoading = true)
                    registerUseCase(
                        email = emailState.value.text,
                        password = passwordState.value.text
                    ).onEach { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _state.value = RegisterState(isLoading = true)
                                d("Registration", "Loading")
                            }
                            is Resource.Success -> {
                                _state.value = RegisterState(isLoading = false)
                                _eventFlow.emit(
                                    UiEvent.Navigate(Screen.SplashScreen.route)
                                )
                                d("Registration", "Successful")
                            }
                            is Resource.Error -> {
                                d("Registration", "Error")
                                if (result.data?.emailError != null) {
                                    _isErrorEmail.value = true
                                    if (result.data.passwordError != null) {
                                        _isErrorPassword.value = true
                                    }
                                } else if (result.data?.passwordError != null) {
                                    _isErrorPassword.value = true
                                } else {

                                }
                                _state.value = RegisterState(isLoading = false)
                            }
                        }
                    }.launchIn(viewModelScope)

                }
            }
        }
    }
}