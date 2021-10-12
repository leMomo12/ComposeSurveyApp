package com.mnowo.composesurveyapp.core.domain

import com.mnowo.composesurveyapp.core.util.Error

data class TextFieldState(
    val text: String = "",
    val error: Error? = null
)
