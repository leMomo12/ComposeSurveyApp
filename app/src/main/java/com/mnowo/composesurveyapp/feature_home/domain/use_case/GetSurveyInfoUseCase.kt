package com.mnowo.composesurveyapp.feature_home.domain.use_case

import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.feature_answer.domain.repository.AnswerRepository
import com.mnowo.composesurveyapp.feature_home.domain.models.SurveyInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSurveyInfoUseCase @Inject constructor(
    private val answerRepository: AnswerRepository
) {
    operator fun invoke() : Flow<Resource<SurveyInfo>> = flow {

    }
}