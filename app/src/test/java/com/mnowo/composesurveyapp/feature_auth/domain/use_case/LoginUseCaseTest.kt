package com.mnowo.composesurveyapp.feature_auth.domain.use_case

import com.google.common.truth.Truth
import com.mnowo.composesurveyapp.feature_auth.data.repository.FakeAuthRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {


    private lateinit var loginUseCase: LoginUseCase
    private lateinit var fakeRepository: FakeAuthRepository

    @Before
    fun setUp() {
        fakeRepository = FakeAuthRepository()
        loginUseCase = LoginUseCase(fakeRepository)

    }

    @Test
    fun `empty password, returns false`() {
        var result: Boolean? = null
        runBlocking {
            val respond = loginUseCase.invoke("fsa@df.de", "").collectLatest {
                if(it.data?.passwordError == true) {
                    result = true
                }
            }
        }
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `password with less than six characters, returns false`() {
        var result: Boolean? = null
        runBlocking {
            val respond = loginUseCase.invoke("fsa@df.de", "12345").collectLatest {
                if(it.message != null) {
                    result = true
                }
            }
        }
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `empty email, returns false`() {
        var result: Boolean? = null
        runBlocking {
            val respond = loginUseCase.invoke("", "fsadfsadf").collectLatest {
                if(it.data?.emailError == true) {
                    result = true
                }
            }
        }
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `invalid email, returns false`() {
        var result: Boolean? = null
        runBlocking {
            val respond = loginUseCase.invoke("asfasdf.de", "fsadfsadf").collectLatest {
                if(it.data?.emailError == true) {
                    result = true
                }
            }
        }
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `valid inputs, return true`() {
        var result: Boolean? = null
        runBlocking {
            val respond = loginUseCase.invoke("asf@asdf.de", "fsadfsadf").collectLatest {
                if(it.data?.emailError != true && it.data?.passwordError != true) {
                    result = true
                }
            }
        }
        Truth.assertThat(result).isTrue()
    }
}