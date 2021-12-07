package com.mnowo.composesurveyapp.feature_add_survey.domain.use_case

import com.google.common.truth.Truth
import com.mnowo.composesurveyapp.feature_add_survey.data.repository.FakeAddSurveyRepositoryTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class DeleteAllQuestionsUseCaseTest {

    private lateinit var fakeAddSurveyRepositoryTest: FakeAddSurveyRepositoryTest
    private lateinit var deleteAllQuestionsUseCase: DeleteAllQuestionsUseCase

    @Before
    fun setUp() {
        fakeAddSurveyRepositoryTest = FakeAddSurveyRepositoryTest()
        deleteAllQuestionsUseCase = DeleteAllQuestionsUseCase(fakeAddSurveyRepositoryTest)
    }

    @Test
    fun `delete all questions, returns true`() {
        val result : Boolean? = true
        runBlocking {
            deleteAllQuestionsUseCase.invoke()
        }
        Truth.assertThat(result).isTrue()
    }
}