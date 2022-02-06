package com.mnowo.composesurveyapp.feature_answer.domain.use_case

import android.util.Log
import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.core.util.UiText
import com.mnowo.composesurveyapp.feature_answer.domain.repository.AnswerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SurveyRatingUseCase @Inject constructor(
    private val answerRepository: AnswerRepository
) {

    operator fun invoke(thumbUp: Boolean, surveyName: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading<Boolean>(data = true))
        val result = answerRepository.addSurveyRating(thumbUp = thumbUp, surveyName = surveyName)

        if (result.isSuccessful == true && result.errorMessage == null) {
            emit(Resource.Success<Boolean>(data = true))
        } else {
            emit(Resource.Error<Boolean>(message = result.errorMessage ?: UiText.unknownError().toString()))
        }
    }
}