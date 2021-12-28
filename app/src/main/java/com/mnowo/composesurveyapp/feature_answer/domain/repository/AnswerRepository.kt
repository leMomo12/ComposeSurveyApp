package com.mnowo.composesurveyapp.feature_answer.domain.repository

import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion

interface AnswerRepository {

    suspend fun getSurvey(collectionPath: String) : MutableList<GetQuestion>
}