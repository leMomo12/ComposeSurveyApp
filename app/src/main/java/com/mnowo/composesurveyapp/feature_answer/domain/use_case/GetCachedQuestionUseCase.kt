package com.mnowo.composesurveyapp.feature_answer.domain.use_case

import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion
import com.mnowo.composesurveyapp.feature_answer.domain.repository.AnswerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCachedQuestionUseCase @Inject constructor(
    private val answerRepository: AnswerRepository
) {

    operator fun invoke(id: Int) : Flow<Resource<GetQuestion>> = flow {
        emit(Resource.Loading<GetQuestion>())
        try {
            val result = answerRepository.getCachedQuestion(id)
            emit(Resource.Success<GetQuestion>(data = result))
        } catch (e: Exception) {
            emit(Resource.Error<GetQuestion>(message = e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}