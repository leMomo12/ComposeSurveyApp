package com.mnowo.composesurveyapp.feature_auth.domain.models

data class LoginResult(
    val emailError: Boolean? = null,
    val passwordError: Boolean? = null,
    val loginResult: Boolean? = null
)
