package com.mnowo.composesurveyapp.feature_answer.domain.repository

import com.mnowo.composesurveyapp.core.domain.models.RemoteDbRespond
import com.mnowo.composesurveyapp.feature_answer.domain.models.Answer
import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion

interface AnswerRepository {

    suspend fun getSurvey(collectionPath: String) : List<GetQuestion>

    suspend fun cachingAnswer(answer: Answer)

    suspend fun cachingSurveyQuestions(getQuestion: GetQuestion)

    suspend fun getCachedQuestion(): List<GetQuestion>

    suspend fun deleteAllCachedAnswers()

    suspend fun deleteAllCachedQuestions()

    suspend fun addUserAnswer(answer: Answer) : RemoteDbRespond

    suspend fun getCachedAnswers() : List<Answer>

    suspend fun addSurveyRating(thumbUp: Boolean, surveyName: String): RemoteDbRespond
}