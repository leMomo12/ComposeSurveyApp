package com.mnowo.composesurveyapp.feature_answer.data.repository

import com.mnowo.composesurveyapp.feature_answer.data.local.AnswerDao
import com.mnowo.composesurveyapp.feature_answer.data.remote.AnswerRemoteDb
import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion
import com.mnowo.composesurveyapp.feature_answer.domain.repository.AnswerRepository
import javax.inject.Inject

class AnswerRepositoryImpl @Inject constructor(
    private val answerRemoteDb: AnswerRemoteDb,
    private val answerDao: AnswerDao
) : AnswerRepository {

    override suspend fun getSurvey(collectionPath: String) : List<GetQuestion> {
        return answerRemoteDb.getSurvey(collectionPath = collectionPath)
    }

    override suspend fun cachingSurveyQuestions(getQuestion: GetQuestion) {
        return answerDao.cachingSurveyQuestions(getQuestion = getQuestion)
    }

    override suspend fun getCachedQuestion(id: Int): GetQuestion {
        return answerDao.getCachedQuestion(id = id)
    }

}