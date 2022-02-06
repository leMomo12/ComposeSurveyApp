package com.mnowo.composesurveyapp.feature_answer.data.remote.util

import com.mnowo.composesurveyapp.feature_answer.domain.models.Answer

object UserAnswerUtil {

    fun getUserAnswer(answer: Answer) : String {
        return when(answer.answer) {
            Answer.AnswerOptions.FIRST -> "firstQuestion"
            Answer.AnswerOptions.SECOND -> "secondQuestion"
            Answer.AnswerOptions.THIRD -> "thirdQuestion"
            else -> "fourthQuestion"
        }
    }
}