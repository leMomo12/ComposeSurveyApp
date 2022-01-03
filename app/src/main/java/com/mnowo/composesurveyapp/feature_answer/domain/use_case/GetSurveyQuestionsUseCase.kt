package com.mnowo.composesurveyapp.feature_answer.domain.use_case

import android.util.Log
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
        emit(Resource.Loading<List<GetQuestion>>())
        val result = answerRepository.getSurvey(collectionPath = collectionPath)
        var succcessful = true
        var errorMessage: String? = null

        for (document in result) {
            if (document.error != null) {
                succcessful = false
                errorMessage = document.error.toString()
            }
        }

        if(succcessful) {
            for (question in result) {
                val item = question.copy(id = 0)
                answerRepository.cachingSurveyQuestions(item)
            }
            Log.d("GetQuestion", "success")
            emit(Resource.Success<List<GetQuestion>>(data = result))
        } else {
            Log.d("GetQuestion", "error $errorMessage")
            emit(Resource.Error<List<GetQuestion>>(message = errorMessage ?: "An unexpected error occurred"))
        }
    }
}