package com.mnowo.composesurveyapp.feature_auth.domain.models

import com.mnowo.composesurveyapp.core.models.RemoteDbRespond

data class RegisterResult(
    val emailError: Boolean? = null,
    val passwordError: Boolean? = null,
    val registerResult: RemoteDbRespond? = null
)