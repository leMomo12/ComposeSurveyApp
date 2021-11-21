package com.mnowo.composesurveyapp.feature_add_survey.domain.use_case

import android.util.Log.d
import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.GetSurveyQuestionsResult
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyQuestion
import com.mnowo.composesurveyapp.feature_add_survey.domain.repositrory.AddSurveyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

class AddSurveyUseCase @Inject constructor(
    private val addSurveyRepository: AddSurveyRepository
) {

    operator fun invoke() : Flow<Resource<GetSurveyQuestionsResult>> = flow {
        emit(Resource.Loading<GetSurveyQuestionsResult>())
        try {
            val questionListData = addSurveyRepository.getSurveyQuestions()

            val titleAndDescription = addSurveyRepository.getTitleAndDescription()

            addSurveyRepository.addSurveyTitleAndDescription(titleAndDescription)

            for(data in questionListData) {
                d("AddSurvey", "Item: ${data.toString()}")
                addSurveyRepository.addSurvey(data, titleAndDescription)
            }

        } catch (e: Exception) {
            d("AddSurvey", "Error")
            emit(Resource.Error<GetSurveyQuestionsResult>(message = "Unexpected error occurred"))
        }
    }
}