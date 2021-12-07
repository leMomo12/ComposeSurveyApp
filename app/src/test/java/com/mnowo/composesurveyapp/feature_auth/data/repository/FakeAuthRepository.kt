package com.mnowo.composesurveyapp.feature_auth.data.repository

import com.mnowo.composesurveyapp.core.models.RemoteDbRespond
import com.mnowo.composesurveyapp.feature_auth.domain.repository.AuthRepository
import org.junit.Assert.*

class FakeAuthRepository : AuthRepository {
    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): RemoteDbRespond {
        return if(password.length < 6) {
            RemoteDbRespond(errorMessage = "The password must hav at least 6 characters.")
        } else if(!email.contains("@")) {
            RemoteDbRespond(isSuccessful = false)
        } else if(password.trim().isBlank()) {
            RemoteDbRespond(isSuccessful = false)
        } else if(email.trim().isBlank()) {
            RemoteDbRespond(isSuccessful = false)
        } else {
            RemoteDbRespond(isSuccessful = true)
        }
    }

    override suspend fun registerWithEmailAndPassword(
        email: String,
        password: String
    ): RemoteDbRespond {
        return if(password.length < 6) {
            RemoteDbRespond(errorMessage = "The password must hav at least 6 characters.")
        } else if(!email.contains("@")) {
            RemoteDbRespond(isSuccessful = false)
        } else if(password.trim().isBlank()) {
            RemoteDbRespond(isSuccessful = false)
        } else if(email.trim().isBlank()) {
            RemoteDbRespond(isSuccessful = false)
        } else {
            RemoteDbRespond(isSuccessful = true)
        }
    }

    override suspend fun deleteAllQuestions() {

    }


}