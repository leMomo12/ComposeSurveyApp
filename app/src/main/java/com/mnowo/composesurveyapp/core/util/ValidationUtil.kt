package com.mnowo.composesurveyapp.core.util

import com.mnowo.composesurveyapp.core.domain.models.ValidationResult

object ValidationUtil {

    fun validateText(text: String): ValidationResult {
        return if (text.trim().isBlank()) {
            ValidationResult(isInvalidInput = true)
        } else {
            ValidationResult(isValidInput = true)
        }
    }

    fun validateEmail(email: String): ValidationResult {
        return if (email.trim().isBlank()) {
            ValidationResult(isInvalidInput = true)
        } else if(!email.contains("@")) {
            ValidationResult(isInvalidEmail = true)
        } else {
            ValidationResult(isValidInput = true)
        }
    }
}