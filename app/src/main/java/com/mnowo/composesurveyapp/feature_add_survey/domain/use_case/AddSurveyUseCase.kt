package com.mnowo.composesurveyapp.feature_add_survey.domain.use_case

import android.util.Log.d
import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.core.util.Constants
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.GetSurveyQuestionsResult
import com.mnowo.composesurveyapp.feature_add_survey.domain.repositrory.AddSurveyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddSurveyUseCase @Inject constructor(
    private val addSurveyRepository: AddSurveyRepository
) {

    operator fun invoke(): Flow<Resource<GetSurveyQuestionsResult>> = flow {
        emit(Resource.Loading<GetSurveyQuestionsResult>())
        try {
            val questionListData = addSurveyRepository.getSurveyQuestions()

            val titleAndDescription = addSurveyRepository.getTitleAndDescription()

            val titleAndDescriptionResult =
                addSurveyRepository.addSurveyTitleAndDescription(titleAndDescription)

            val titleAndDescriptionInfoResult =
                addSurveyRepository.addSurveyTitleAndDescriptionToInfo(titleAndDescription)

            var surveyStatus = true

            if (titleAndDescriptionResult.isSuccessful == true && questionListData.isNotEmpty() && titleAndDescriptionInfoResult.isSuccessful == true) {
                for (data in questionListData) {
                    d("AddSurvey", "Item: ${data.toString()}")
                    val surveyResult = addSurveyRepository.addSurvey(data, titleAndDescription)
                    if (surveyResult.isSuccessful == false) {
                        emit(
                            Resource.Error<GetSurveyQuestionsResult>(
                                message = titleAndDescriptionResult.errorMessage
                                    ?: Constants.UNEXPECTED_ERROR
                            )
                        )
                        surveyStatus = false
                    }
                }
                if (!surveyStatus) {
                    Resource.Error<GetSurveyQuestionsResult>(
                        Constants.UNEXPECTED_ERROR
                    )
                } else {
                    emit(Resource.Success<GetSurveyQuestionsResult>(data = GetSurveyQuestionsResult()))
                    d("AddSurvey", "Successfully added Survey")
                }
            } else {
                when {
                    titleAndDescriptionInfoResult.isSuccessful != true -> {
                        emit(
                            Resource.Error<GetSurveyQuestionsResult>(
                                message = titleAndDescriptionInfoResult.errorMessage
                                    ?: Constants.UNEXPECTED_ERROR
                            )
                        )
                    }
                    titleAndDescriptionResult.isSuccessful != true -> {
                        emit(
                            Resource.Error<GetSurveyQuestionsResult>(
                                message = titleAndDescriptionResult.errorMessage
                                    ?: Constants.UNEXPECTED_ERROR
                            )
                        )
                    }
                    else -> {
                        emit(
                            Resource.Error<GetSurveyQuestionsResult>(
                                message = "Empty list"
                            )
                        )
                    }
                }
            }

        } catch (e: Exception) {
            d("AddSurvey", "Error")
            emit(Resource.Error<GetSurveyQuestionsResult>(message = Constants.UNEXPECTED_ERROR))
        }
    }
}