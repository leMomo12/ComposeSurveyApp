package com.mnowo.composesurveyapp.feature_answer.domain.use_case

import com.mnowo.composesurveyapp.feature_answer.domain.repository.AnswerRepository
import javax.inject.Inject

class DeleteCachedAnswerUseCase @Inject constructor(
    private val answerRepository: AnswerRepository
){

    suspend operator fun invoke() = answerRepository.deleteAllCachedAnswers()
}