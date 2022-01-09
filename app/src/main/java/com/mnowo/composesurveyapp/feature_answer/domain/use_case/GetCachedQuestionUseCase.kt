package com.mnowo.composesurveyapp.feature_answer.domain.use_case

import android.util.Log.d
import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion
import com.mnowo.composesurveyapp.feature_answer.domain.repository.AnswerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCachedQuestionUseCase @Inject constructor(
    private val answerRepository: AnswerRepository
) {

    @ExperimentalCoroutinesApi
    operator fun invoke() : Flow<Resource<List<GetQuestion>>> = channelFlow {
        d("GetSurvey", "here")
        send(Resource.Loading())
        try {
            val result = answerRepository.getCachedQuestion()
            send(Resource.Success<List<GetQuestion>>(data = result))
            d("GetSurvey", "result: $result")
        } catch (e: Exception) {
            d("GetSurvey", "${e.localizedMessage}")
        }
    }
}