package com.mnowo.composesurveyapp.feature_auth.domain.models

import com.mnowo.composesurveyapp.core.domain.models.RemoteDbRespond

data class LoginResult(
    val emailError: Boolean? = null,
    val passwordError: Boolean? = null,
    val loginResult: RemoteDbRespond? = null
)
