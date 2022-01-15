package com.mnowo.composesurveyapp.feature_answer.domain.use_case

import com.mnowo.composesurveyapp.feature_answer.domain.models.Answer
import com.mnowo.composesurveyapp.feature_answer.domain.repository.AnswerRepository
import javax.inject.Inject

class CachingAnswerUseCase @Inject constructor(
   private val repository: AnswerRepository
) {

    suspend operator fun invoke(answer: Answer) = repository.cachingAnswer(answer = answer)
}