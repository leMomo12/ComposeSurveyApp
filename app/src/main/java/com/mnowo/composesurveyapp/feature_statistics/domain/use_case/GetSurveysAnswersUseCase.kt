package com.mnowo.composesurveyapp.feature_statistics.domain.use_case

import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion
import com.mnowo.composesurveyapp.feature_statistics.data.repository.StatisticRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSurveysAnswersUseCase @Inject constructor(
    private val repositoryImpl: StatisticRepositoryImpl
) {

    operator fun invoke(collectionPath: String): Flow<Resource<List<GetQuestion>>> = flow {
        emit(Resource.Loading<List<GetQuestion>>())

        val result = repositoryImpl.getAnswer(collectionPath)

        if (!result.isNullOrEmpty()) {
            emit(Resource.Success<List<GetQuestion>>(data = result))
        }
    }
}