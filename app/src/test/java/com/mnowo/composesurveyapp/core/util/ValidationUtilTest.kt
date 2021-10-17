package com.mnowo.composesurveyapp.core.util


import com.google.common.truth.Truth.assertThat
import org.junit.Test


class ValidationUtilTest {

    @Test
    fun `valid text, returns validInput`() {
        val result = ValidationUtil.validateText("Valid text")

        assertThat(result.isValidInput).isTrue()
        assertThat(result.isInvalidInput).isNull()
        assertThat(result.isInvalidEmail).isNull()
    }

    @Test
    fun `blank text, returns invalidInput`() {
        val result = ValidationUtil.validateText(" ")

        assertThat(result.isInvalidInput).isTrue()
        assertThat(result.isValidInput).isNull()
        assertThat(result.isInvalidEmail).isNull()
    }

    @Test
    fun `valid email, returns invalidEmail is false`() {
        val result = ValidationUtil.validateEmail("a@a.de")

        assertThat(result.isInvalidInput).isNull()
        assertThat(result.isValidInput).isTrue()
        assertThat(result.isInvalidEmail).isNull()
    }

    @Test
    fun `invalid email, returns invalidEmail is true`() {
        val result = ValidationUtil.validateEmail("aa.de")

        assertThat(result.isInvalidInput).isNull()
        assertThat(result.isValidInput).isNull()
        assertThat(result.isInvalidEmail).isTrue()
    }

    @Test
    fun `empty email, returns invalidEmail is true`() {
        val result = ValidationUtil.validateEmail(" ")

        assertThat(result.isInvalidInput).isTrue()
        assertThat(result.isValidInput).isNull()
        assertThat(result.isInvalidEmail).isNull()
    }
}
