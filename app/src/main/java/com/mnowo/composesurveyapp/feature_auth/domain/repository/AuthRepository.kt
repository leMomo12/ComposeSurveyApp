package com.mnowo.composesurveyapp.feature_auth.domain.repository

import com.mnowo.composesurveyapp.core.models.RemoteDbRespond

interface AuthRepository {

    suspend fun signInWithEmailAndPassword(email: String, password: String) : RemoteDbRespond

    suspend fun registerWithEmailAndPassword(email: String, password: String) : RemoteDbRespond

    suspend fun deleteAllQuestions()
}