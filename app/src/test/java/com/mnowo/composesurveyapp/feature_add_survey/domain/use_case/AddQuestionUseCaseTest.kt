package com.mnowo.composesurveyapp.feature_add_survey.domain.use_case

import com.google.common.truth.Truth
import com.mnowo.composesurveyapp.feature_add_survey.data.repository.FakeAddSurveyRepositoryTest
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyQuestion
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddQuestionUseCaseTest {
    private lateinit var addQuestionUseCase: AddQuestionUseCase
    private lateinit var fakeAddSurveyRepositoryTest: FakeAddSurveyRepositoryTest

    @Before
    fun setUp() {
        fakeAddSurveyRepositoryTest = FakeAddSurveyRepositoryTest()
        addQuestionUseCase = AddQuestionUseCase(fakeAddSurveyRepositoryTest)

    }

    @Test
    fun `empty title, returns false`() {
        var result: Boolean? = null
        runBlocking {
            addQuestionUseCase.invoke(
                SurveyQuestion(
                    1,
                    "",
                    "sdfsdf",
                    "fsdfasd",
                    "sdfsadf",
                    "sdfsdaf"
                )
            ).collectLatest {
                if (it.data?.titleError == true && it.message != null && it.data?.lessThanTwoFilledAnswerOptions == null) {
                    result = false
                }
            }
        }
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `less than two fields, returns false`() {
        var result: Boolean? = null
        runBlocking {
            addQuestionUseCase.invoke(
                SurveyQuestion(
                    1,
                    "dasdasddas",
                    "sdfsdf",
                    "",
                    "",
                    ""
                )
            ).collectLatest {
                if (it.data?.lessThanTwoFilledAnswerOptions == true && it.message != null && it.data?.titleError == null) {
                    result = false
                }
            }
        }
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `everything empty, returns false`() {
        var result: Boolean? = null
        runBlocking {
            addQuestionUseCase.invoke(
                SurveyQuestion(
                    1,
                    "",
                    "",
                    "",
                    "",
                    ""
                )
            ).collectLatest {
                if (it.data?.titleError == true || it.message != null || it.data?.lessThanTwoFilledAnswerOptions == true) {
                    result = false
                }
            }
        }
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `nothing empty, returns true`() {
        var result: Boolean? = null
        runBlocking {
            addQuestionUseCase.invoke(
                SurveyQuestion(
                    1,
                    "sfsdaf",
                    "sdfsdf",
                    "fsdfasd",
                    "sdfsadf",
                    "sdfsdaf"
                )
            ).collectLatest {
                if (it.data?.titleError == null && it.message == null && it.data?.lessThanTwoFilledAnswerOptions == null) {
                    result = true
                }
            }
        }
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `two filled fields, returns true`() {
        var result: Boolean? = null
        runBlocking {
            addQuestionUseCase.invoke(
                SurveyQuestion(
                    1,
                    "dfdfssdf",
                    "sdfsdf",
                    "fsdfasd",
                    "",
                    ""
                )
            ).collectLatest {
                if (it.data?.titleError == null && it.message == null && it.data?.lessThanTwoFilledAnswerOptions == null) {
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
            addQuestionUseCase.invoke(
                SurveyQuestion(
                    1,
                    "dfdfssdf",
                    "sdfsdf",
                    "fsdfasd",
                    "sffsfsa",
                    ""
                )
            ).collectLatest {
                if (it.data?.titleError == null && it.message == null && it.data?.lessThanTwoFilledAnswerOptions == null) {
                    result = true
                }
            }
        }
        Truth.assertThat(result).isTrue()
    }
}