package com.mnowo.composesurveyapp.feature_answer.domain.use_case

import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion
import com.mnowo.composesurveyapp.feature_answer.domain.repository.AnswerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSurveyQuestionsUseCase @Inject constructor(
    private val answerRepository: AnswerRepository
) {

    operator fun invoke(collectionPath: String): Flow<Resource<MutableList<GetQuestion>>> = flow {
        emit(Resource.Loading<MutableList<GetQuestion>>())
        val result = answerRepository.getSurvey(collectionPath = collectionPath)
        var succcessful: Boolean = true
        var errorMessage: String? = null

        for (document in result) {
            if (document.error != null) {
                succcessful = false
                errorMessage = document.error.toString()
            }
        }

        if(succcessful) {
            emit(Resource.Success(data = result))
        } else {
            emit(Resource.Error(message = errorMessage ?: "An unexpected error occurred"))
        }
    }
}