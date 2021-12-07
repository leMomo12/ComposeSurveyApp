package com.mnowo.composesurveyapp.feature_add_survey.domain.use_case

import com.google.common.truth.Truth
import com.mnowo.composesurveyapp.feature_add_survey.data.repository.FakeAddSurveyRepositoryTest
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyTitleAndDescription
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AddSurveyTitleAndDescriptionUseCaseTest {
    private lateinit var addSurveyTitleAndDescriptionUseCase: AddSurveyTitleAndDescriptionUseCase
    private lateinit var fakeAddSurveyRepositoryTest: FakeAddSurveyRepositoryTest

    @Before
    fun setUp() {
        fakeAddSurveyRepositoryTest = FakeAddSurveyRepositoryTest()
        addSurveyTitleAndDescriptionUseCase =
            AddSurveyTitleAndDescriptionUseCase(fakeAddSurveyRepositoryTest)
    }

    @Test
    fun `empty title, returns false`() {
        var result: Boolean? = null
        runBlocking {
            addSurveyTitleAndDescriptionUseCase.invoke(
                SurveyTitleAndDescription(
                    1,
                    "",
                    "sfssfdsfd"
                )
            ).collectLatest {
                if (it.data?.titleError == true && it.data?.descriptionError == null && it.message != null) {
                    result = false
                }
            }
        }
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `empty description, returns false`() {
        var result: Boolean? = null
        runBlocking {
            addSurveyTitleAndDescriptionUseCase.invoke(SurveyTitleAndDescription(1, "fsadfsdf", ""))
                .collectLatest {
                    if (it.data?.titleError == null && it.data?.descriptionError == true && it.message != null) {
                        result = false
                    }
                }
        }
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `title and description empty, returns false`() {
        var result: Boolean? = null
        runBlocking {
            addSurveyTitleAndDescriptionUseCase.invoke(SurveyTitleAndDescription(1, " ", " "))
                .collectLatest {
                    if (it.data?.titleError == true && it.data?.descriptionError == true && it.message != null) {
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
            addSurveyTitleAndDescriptionUseCase.invoke(
                SurveyTitleAndDescription(
                    1,
                    "",
                    "sfssfdsfd"
                )
            ).collectLatest {
                if (it.data?.titleError == null && it.data?.descriptionError == null) {
                    result = true
                }
            }
        }
        Truth.assertThat(result).isTrue()
    }
}