package com.mnowo.composesurveyapp.feature_answer.domain.use_case

import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.feature_answer.domain.models.Answer
import com.mnowo.composesurveyapp.feature_answer.domain.repository.AnswerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AddUserAnswerUseCase @Inject constructor(
    private val answerRepository: AnswerRepository
) {

    operator fun invoke(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading<Boolean>())
        val cachedAnswerList = answerRepository.getCachedAnswers()

        var error = false

        if (!cachedAnswerList.isNullOrEmpty()) {
            for (result in cachedAnswerList) {
                val dbRespond = answerRepository.addUserAnswer(result)
                if (dbRespond.errorMessage != null) {
                    emit(
                        Resource.Error<Boolean>(
                            message = dbRespond.errorMessage ?: "An unexpected error occurred"
                        )
                    )
                    error = true
                    break
                }
            }
            if(error == false) {
                emit(Resource.Success<Boolean>(true))
            }
        }
    }
}