package com.mnowo.composesurveyapp.feature_add_survey.domain.use_case

import com.mnowo.composesurveyapp.core.domain.models.SavingOwnSurvey
import com.mnowo.composesurveyapp.feature_add_survey.domain.repositrory.AddSurveyRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SavingOwnSurveyUseCase @Inject constructor(
    private val addSurveyRepository: AddSurveyRepository
) {

    operator fun invoke() = flow<Any> {
        val result = addSurveyRepository.getTitleAndDescription()
        addSurveyRepository.savingOwnSurvey(savingOwnSurvey = SavingOwnSurvey(0, result.title, result.description))
    }
}