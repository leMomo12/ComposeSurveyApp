package com.mnowo.composesurveyapp.feature_auth.domain.use_case

import com.mnowo.composesurveyapp.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteAllQuestionsUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke() = flow<Any> {
        authRepository.deleteAllQuestions()
    }
}