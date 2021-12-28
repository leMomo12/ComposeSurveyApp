package com.mnowo.composesurveyapp.feature_answer.data.repository

import com.mnowo.composesurveyapp.feature_answer.data.remote.AnswerRemoteDb
import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion
import com.mnowo.composesurveyapp.feature_answer.domain.repository.AnswerRepository
import javax.inject.Inject

class AnswerRepositoryImpl @Inject constructor(
    private val answerRemoteDb: AnswerRemoteDb
) : AnswerRepository {

    override suspend fun getSurvey(collectionPath: String) : MutableList<GetQuestion> {
        return answerRemoteDb.getSurvey(collectionPath = collectionPath)
    }

}