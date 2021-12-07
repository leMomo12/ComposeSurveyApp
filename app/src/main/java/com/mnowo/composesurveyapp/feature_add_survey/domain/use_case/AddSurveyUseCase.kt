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

    operator fun invoke(): Flow<Resource<GetSurveyQuestionsResult>> = flow {
        emit(Resource.Loading<GetSurveyQuestionsResult>())
        try {
            val questionListData = addSurveyRepository.getSurveyQuestions()

            val titleAndDescription = addSurveyRepository.getTitleAndDescription()

            val titleAndDescriptionResult = addSurveyRepository.addSurveyTitleAndDescription(titleAndDescription)

            var surveyStatus = true

            if (titleAndDescriptionResult.isSuccessful == true && questionListData.isNotEmpty()) {
                for (data in questionListData) {
                    d("AddSurvey", "Item: ${data.toString()}")
                    val surveyResult = addSurveyRepository.addSurvey(data, titleAndDescription)
                    if(surveyResult.isSuccessful == false) {
                        emit(
                            Resource.Error<GetSurveyQuestionsResult>(
                                message = titleAndDescriptionResult.errorMessage
                                    ?: "Unexpected error occurred"
                            )
                        )
                        surveyStatus = false
                    }
                }
                if(!surveyStatus) {
                    Resource.Error<GetSurveyQuestionsResult>(
                        message = "Unexpected error occurred"
                    )
                } else {
                    emit(Resource.Success<GetSurveyQuestionsResult>(data = GetSurveyQuestionsResult()))
                    d("AddSurvey", "Successfully added Survey")
                }
            } else {
                emit(
                    Resource.Error<GetSurveyQuestionsResult>(
                        message = titleAndDescriptionResult.errorMessage
                            ?: "Unexpected error occurred"
                    )
                )
            }


        } catch (e: Exception) {
            d("AddSurvey", "Error")
            emit(Resource.Error<GetSurveyQuestionsResult>(message = "Unexpected error occurred"))
        }
    }
}