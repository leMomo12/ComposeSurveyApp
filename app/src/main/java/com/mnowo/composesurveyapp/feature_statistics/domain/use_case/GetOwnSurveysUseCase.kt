package com.mnowo.composesurveyapp.feature_statistics.domain.use_case

import com.mnowo.composesurveyapp.core.domain.models.SurveyInfo
import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.feature_statistics.domain.repository.StatisticRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOwnSurveysUseCase @Inject constructor(
    private val statisticRepository: StatisticRepository
) {

    operator fun invoke(): Flow<Resource<MutableList<SurveyInfo>>> = flow {
        emit(Resource.Loading<MutableList<SurveyInfo>>())
        val ownSurveyList = statisticRepository.getOwnCachedSurveys()

        val result = statisticRepository.getOwnSurveys(collectionList = ownSurveyList)

        for (item in result) {
            if (!item.errorMessage.isNullOrBlank()) {
                emit(
                    Resource.Error<MutableList<SurveyInfo>>(message = item.errorMessage)
                )
                return@flow
            }
        }
        emit(Resource.Success<MutableList<SurveyInfo>>(data = result))
    }
}