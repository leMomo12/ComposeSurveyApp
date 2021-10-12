package com.mnowo.composesurveyapp.feature_auth.domain.use_case

import android.provider.Settings.Global.getString
import com.mnowo.composesurveyapp.R
import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.feature_auth.domain.models.LoginResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginUseCase {

    operator fun invoke(email: String, password: String) : Flow<Resource<LoginResult>> = flow {
        emit(Resource.Loading())
        val emailError = if(email.trim().isBlank()) LoginResult(emailError = true) else null
        val passwordError = if(password.trim().isBlank()) LoginResult(passwordError = true) else null

        if(emailError != null || passwordError != null) {
            emit(Resource.Error(message = "Invalid Inputs"))
            return@flow
        } else {

        }
    }
}