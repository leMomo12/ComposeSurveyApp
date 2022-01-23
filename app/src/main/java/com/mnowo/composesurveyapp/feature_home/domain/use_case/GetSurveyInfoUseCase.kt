package com.mnowo.composesurveyapp.feature_home.domain.use_case

import android.util.Log.d
import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.core.util.Constants
import com.mnowo.composesurveyapp.feature_answer.domain.repository.AnswerRepository
import com.mnowo.composesurveyapp.feature_home.domain.models.SurveyInfo
import com.mnowo.composesurveyapp.feature_home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetSurveyInfoUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    operator fun invoke() : Flow<Resource<MutableList<SurveyInfo>>> = flow {
        emit(Resource.Loading<MutableList<SurveyInfo>>())
        try {
            val result = homeRepository.getSurveyInfo()

            if(!result.isNullOrEmpty()) {
                d("getSurvey", "yess sir ${result.toString()}")
                emit(Resource.Success<MutableList<SurveyInfo>>(result))
            } else {
                d("getSurvey", "noo sir ${result.toString()}")
                emit(Resource.Error<MutableList<SurveyInfo>>(message = Constants.UNEXPECTED_ERROR))
            }
        } catch (e: Exception) {
            emit(Resource.Error<MutableList<SurveyInfo>>(message = e.localizedMessage ?: Constants.UNEXPECTED_ERROR))
        }
    }
}