package com.mnowo.composesurveyapp.feature_auth.presentation.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

) : ViewModel() {

    private val _state = mutableStateOf<Boolean>(false)
    val state: State<Boolean> = _state

    init {
        _state.value = FirebaseAuth.getInstance().currentUser != null
    }
}