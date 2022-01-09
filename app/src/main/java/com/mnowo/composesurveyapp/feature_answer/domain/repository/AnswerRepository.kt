package com.mnowo.composesurveyapp.feature_answer.domain.repository

import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion

interface AnswerRepository {

    suspend fun getSurvey(collectionPath: String) : List<GetQuestion>

    suspend fun cachingSurveyQuestions(getQuestion: GetQuestion)

    suspend fun getCachedQuestion(): List<GetQuestion>

    suspend fun deleteAllCachedQuestions()
}