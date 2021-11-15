package com.mnowo.composesurveyapp.feature_add_survey.domain.use_case

import android.util.Log.d
import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.core.util.ValidationUtil
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyQuestion
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyQuestionResult
import com.mnowo.composesurveyapp.feature_add_survey.domain.repositrory.AddSurveyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddQuestionUseCase @Inject constructor(
    private val addSurveyRepository: AddSurveyRepository
){

    operator fun invoke(surveyQuestion: SurveyQuestion): Flow<Resource<SurveyQuestionResult>> = flow {
        emit(Resource.Loading<SurveyQuestionResult>())
        val titleResult = ValidationUtil.validateText(surveyQuestion.questionTitle)

        d("AddQuestion", "Before if")

        if(titleResult.isValidInput == true) {
            d("AddQuestion", "In here")
            var questionAmount = 0
            for(i in 0..3) {
                when(i) {
                    0 -> {
                        d("AddQuestion", "More than zero")
                       val questionOneResult = ValidationUtil.validateText(surveyQuestion.questionOne.toString())
                        if(questionOneResult.isValidInput == true) {
                            questionAmount++
                        }
                    }
                    1 -> {
                        d("AddQuestion", "More than one")
                        val questionTwoResult = ValidationUtil.validateText(surveyQuestion.questionTwo.toString())
                        if(questionTwoResult.isValidInput == true) {
                            questionAmount++
                        }
                    }
                    2 -> {
                        d("AddQuestion", "More than ten")
                        val questionThreeResult = ValidationUtil.validateText(surveyQuestion.questionThree.toString())
                        if(questionThreeResult.isValidInput == true) {
                            questionAmount++
                        }
                    }
                    3 -> {
                        d("AddQuestion", "More than three")
                        val questionFourResult = ValidationUtil.validateText(surveyQuestion.questionFour.toString())
                        if(questionFourResult.isValidInput == true) {
                            questionAmount++
                        }
                    }
                }
            }
            if(questionAmount >= 2) {
                d("AddQuestion", "More than two")
                addSurveyRepository.addQuestion(surveyQuestion)
                emit(Resource.Success<SurveyQuestionResult>(data = SurveyQuestionResult()))
            } else {
                emit(Resource.Error<SurveyQuestionResult>(data = SurveyQuestionResult(lessThanTwoFilledAnswerOptions = true), message = "Less than two answer options"))
            }
        } else {
            emit(Resource.Error<SurveyQuestionResult>(data = SurveyQuestionResult(titleError = true), message = "Empty question title"))
        }
    }
}