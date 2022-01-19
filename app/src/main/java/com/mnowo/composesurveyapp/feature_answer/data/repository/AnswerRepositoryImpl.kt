package com.mnowo.composesurveyapp.feature_answer.data.repository

import android.util.Log.d
import com.mnowo.composesurveyapp.feature_answer.data.local.AnswerDao
import com.mnowo.composesurveyapp.feature_answer.data.remote.AnswerRemoteDb
import com.mnowo.composesurveyapp.feature_answer.domain.models.Answer
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

    override suspend fun cachingAnswer(answer: Answer) {
        return answerDao.cachingAnswer(answer = answer)
    }

    override suspend fun cachingSurveyQuestions(getQuestion: GetQuestion) {
        return answerDao.cachingSurveyQuestions(getQuestion = getQuestion)
    }

    override suspend fun getCachedQuestion(): List<GetQuestion> {
        return answerDao.getCachedQuestion()
    }

    override suspend fun deleteAllCachedAnswers() {
        return answerDao.deleteAllCachedAnswers()
    }

    override suspend fun deleteAllCachedQuestions() {
        return answerDao.deleteAllCachedQuestions()
    }

    override suspend fun addUserAnswer(answer: Answer) {
        return answerRemoteDb.addUserAnswer(answer = answer)
    }

}