package com.mnowo.composesurveyapp.core.models

data class ValidationResult(
    var isValidInput: Boolean? = null,
    var isInvalidInput: Boolean? = null,
    var isInvalidEmail: Boolean? = null
)
