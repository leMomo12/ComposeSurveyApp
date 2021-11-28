package com.mnowo.composesurveyapp.feature_add_survey.domain.use_case

import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.core.util.ValidationUtil
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.AddTitleAndDescriptionResult
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyTitleAndDescription
import com.mnowo.composesurveyapp.feature_add_survey.domain.repositrory.AddSurveyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddSurveyTitleAndDescriptionUseCase @Inject constructor(
    private val addSurveyRepository: AddSurveyRepository
) {

    operator fun invoke(surveyTitleAndDescription: SurveyTitleAndDescription): Flow<Resource<AddTitleAndDescriptionResult>> =
        flow {
            emit(Resource.Loading<AddTitleAndDescriptionResult>())
            val titleResult = ValidationUtil.validateText(surveyTitleAndDescription.title)
            val descriptionResult = ValidationUtil.validateText(surveyTitleAndDescription.description)

            if (titleResult.isValidInput == true && descriptionResult.isValidInput == true) {
                addSurveyRepository.addSurveyTitleAndDescriptionToRoom(surveyTitleAndDescription)
                emit(
                    Resource.Success<AddTitleAndDescriptionResult>(
                        AddTitleAndDescriptionResult(
                            titleError = false,
                            descriptionError = false
                        )
                    )
                )
            } else {
                when {
                    titleResult.isInvalidInput == true -> {
                        if (descriptionResult.isInvalidInput == true) {
                            emit(
                                Resource.Error<AddTitleAndDescriptionResult>(
                                    data = AddTitleAndDescriptionResult(
                                        titleError = true,
                                        descriptionError = true
                                    ), message = "Invalid inputs"
                                )
                            )
                        } else {
                            emit(
                                Resource.Error<AddTitleAndDescriptionResult>(
                                    data = AddTitleAndDescriptionResult(
                                        titleError = true
                                    ), message = "Invalid title"
                                )
                            )
                        }
                    }
                    descriptionResult.isInvalidInput == true -> {
                        emit(
                            Resource.Error<AddTitleAndDescriptionResult>(
                                data = AddTitleAndDescriptionResult(
                                    descriptionError = true
                                ), message = "Invalid description"
                            )
                        )
                    }
                }
            }
        }
}