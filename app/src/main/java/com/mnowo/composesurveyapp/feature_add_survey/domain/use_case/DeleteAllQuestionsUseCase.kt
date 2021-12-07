package com.mnowo.composesurveyapp.feature_add_survey.domain.use_case

import com.mnowo.composesurveyapp.feature_add_survey.domain.repositrory.AddSurveyRepository
import kotlinx.coroutines.flow.flow
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

class DeleteAllQuestionsUseCase @Inject constructor(
    private val addSurveyRepository: AddSurveyRepository
) {

    operator fun invoke() = flow<Any> {
        addSurveyRepository.deleteAllQuestions()
    }

}