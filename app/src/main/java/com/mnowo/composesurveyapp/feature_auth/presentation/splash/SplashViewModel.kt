package com.mnowo.composesurveyapp.feature_auth.presentation.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.mnowo.composesurveyapp.feature_auth.domain.use_case.DeleteAllQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val deleteAllQuestionsUseCase: DeleteAllQuestionsUseCase
) : ViewModel() {

    private val _state = mutableStateOf<Boolean>(false)
    val state: State<Boolean> = _state

    init {
        _state.value = FirebaseAuth.getInstance().currentUser != null
        deleteAllQuestionsUseCase.invoke().launchIn(viewModelScope)
    }
}