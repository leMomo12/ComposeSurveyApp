package com.mnowo.composesurveyapp.feature_answer.domain.use_case

import android.util.Log
import android.util.Log.d
import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion
import com.mnowo.composesurveyapp.feature_answer.domain.repository.AnswerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSurveyQuestionsUseCase @Inject constructor(
    private val answerRepository: AnswerRepository
) {

    operator fun invoke(collectionPath: String): Flow<Resource<List<GetQuestion>>> = flow {
        try {
            emit(Resource.Loading<List<GetQuestion>>())
            val result = answerRepository.getSurvey(collectionPath = collectionPath)
            var succcessful = true
            var errorMessage: String? = null

            d("Caching", result.size.toString())

            for (document in result) {
                if (document.error != null) {
                    succcessful = false
                    errorMessage = document.error.toString()
                }
            }

            if (succcessful) {
                d("Caching", "is successful")
                for (question in result) {
                    d("Caching", "caching item: $question")
                    val item = question.copy(id = 0)
                    answerRepository.cachingSurveyQuestions(item)
                }
                emit(Resource.Success<List<GetQuestion>>(data = result))
            } else {
                Log.d("GetSurvey", "error $errorMessage")
                emit(
                    Resource.Error<List<GetQuestion>>(
                        message = errorMessage ?: "An unexpected error occurred"
                    )
                )
            }
        } catch (e: Exception) {
            emit(
                Resource.Error<List<GetQuestion>>(
                    message = e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        }
    }
}