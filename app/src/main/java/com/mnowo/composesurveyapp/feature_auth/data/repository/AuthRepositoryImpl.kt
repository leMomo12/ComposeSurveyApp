package com.mnowo.composesurveyapp.feature_auth.data.repository

import com.mnowo.composesurveyapp.core.models.RemoteDbRespond
import com.mnowo.composesurveyapp.feature_auth.data.remote.AuthRemoteDb
import com.mnowo.composesurveyapp.feature_auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDb: AuthRemoteDb
) : AuthRepository {

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): RemoteDbRespond {
        return authRemoteDb.signInWithEmailAndPassword(email, password)
    }

    override suspend fun registerWithEmailAndPassword(
        email: String,
        password: String
    ): RemoteDbRespond {
        return authRemoteDb.registerWithEmailAndPassword(email, password)
    }

}