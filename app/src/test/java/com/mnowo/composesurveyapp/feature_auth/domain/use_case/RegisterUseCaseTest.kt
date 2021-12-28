package com.mnowo.composesurveyapp.feature_auth.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.mnowo.composesurveyapp.feature_auth.data.repository.FakeAuthRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class RegisterUseCaseTest {

    private lateinit var registerUseCase: RegisterUseCase
    private lateinit var fakeRepository: FakeAuthRepository

    @Before
    fun setUp() {
        fakeRepository = FakeAuthRepository()
        registerUseCase = RegisterUseCase(fakeRepository)

    }

    @Test
    fun `empty password, returns false`() {
        var result: Boolean? = null
        runBlocking {
            val respond = registerUseCase.invoke("fsa@df.de", "").collectLatest {
               if(it.data?.passwordError == true) {
                   result = true
               }
            }
        }
        assertThat(result).isTrue()
    }

    @Test
    fun `password with less than six characters, returns false`() {
        var result: Boolean? = null
        runBlocking {
            val respond = registerUseCase.invoke("fsa@df.de", "12345").collectLatest {
                if(it.message != null) {
                    result = true
                }
            }
        }
        assertThat(result).isTrue()
    }

    @Test
    fun `empty email, returns false`() {
        var result: Boolean? = null
        runBlocking {
            val respond = registerUseCase.invoke("", "fsadfsadf").collectLatest {
                if(it.data?.emailError == true) {
                    result = true
                }
            }
        }
        assertThat(result).isTrue()
    }

    @Test
    fun `invalid email, returns false`() {
        var result: Boolean? = null
        runBlocking {
            val respond = registerUseCase.invoke("asfasdf.de", "fsadfsadf").collectLatest {
                if(it.data?.emailError == true) {
                    result = true
                }
            }
        }
        assertThat(result).isTrue()
    }

    @Test
    fun `valid inputs, return true`() {
        var result: Boolean? = null
        runBlocking {
            val respond = registerUseCase.invoke("asf@asdf.de", "fsadfsadf").collectLatest {
                if(it.data?.emailError != true && it.data?.passwordError != true) {
                    result = true
                }
            }
        }
        assertThat(result).isTrue()
    }
}
