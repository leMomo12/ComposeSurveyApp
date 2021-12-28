package com.mnowo.composesurveyapp.feature_add_survey.domain.use_case

import com.google.common.truth.Truth
import com.mnowo.composesurveyapp.feature_add_survey.data.repository.FakeAddSurveyRepositoryTest
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyQuestion
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class AddSurveyUseCaseTest {

    private lateinit var addSurveyUseCase: AddQuestionUseCase
    private lateinit var fakeAddSurveyRepositoryTest: FakeAddSurveyRepositoryTest

    @Before
    fun setUp() {
        fakeAddSurveyRepositoryTest = FakeAddSurveyRepositoryTest()
        addSurveyUseCase = AddQuestionUseCase(fakeAddSurveyRepositoryTest)
    }


    @Test
    fun `empty title, returns false`() {
        var result: Boolean? = null
        runBlocking {
            addSurveyUseCase.invoke(
                surveyQuestion = SurveyQuestion(
                    1,
                    "",
                    "sadfsdf",
                    "fsadfsd",
                    "sdffsd",
                    "sdfsfd"
                )
            ).collectLatest {
                if(it.data?.titleError == true && it.data?.lessThanTwoFilledAnswerOptions == null) {
                    result = false
                }
            }
        }
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `less than two filled fields, returns false`() {
        var result: Boolean? = null
        runBlocking {
            addSurveyUseCase.invoke(
                surveyQuestion = SurveyQuestion(
                    1,
                    "sasas",
                    "sadfsdf",
                    "",
                    "",
                    ""
                )
            ).collectLatest {
                if(it.data?.titleError == null && it.data?.lessThanTwoFilledAnswerOptions == true) {
                    result = false
                }
            }
        }
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `two filled fields, returns true`() {
        var result: Boolean? = null
        runBlocking {
            addSurveyUseCase.invoke(
                surveyQuestion = SurveyQuestion(
                    1,
                    "fsdsdfsd",
                    "",
                    "fsadfsd",
                    "",
                    "sadfsdf"
                )
            ).collectLatest {
                if(it.data?.titleError == null && it.data?.lessThanTwoFilledAnswerOptions == null) {
                    result = true
                }
            }
        }
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `three filled fields, returns true`() {
        var result: Boolean? = null
        runBlocking {
            addSurveyUseCase.invoke(
                surveyQuestion = SurveyQuestion(
                    1,
                    "fsdsdfsd",
                    "",
                    "fsadfsd",
                    "sdsfdsad",
                    "sadfsdf"
                )
            ).collectLatest {
                if(it.data?.titleError == null && it.data?.lessThanTwoFilledAnswerOptions == null) {
                    result = true
                }
            }
        }
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `everything filled in, returns true`() {
        var result: Boolean? = null
        runBlocking {
            addSurveyUseCase.invoke(
                surveyQuestion = SurveyQuestion(
                    1,
                    "fsdsdfsd",
                    "sadfsdf",
                    "fsadfsd",
                    "sdsdsdff",
                    "fsdfdssd"
                )
            ).collectLatest {
                if(it.data?.titleError == null && it.data?.lessThanTwoFilledAnswerOptions == null) {
                    result = true
                }
            }
        }
        Truth.assertThat(result).isTrue()
    }
}