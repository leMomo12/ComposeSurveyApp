package com.mnowo.composesurveyapp.feature_auth.data.remote

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mnowo.composesurveyapp.core.models.RemoteDbRespond
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class AuthRemoteDb @Inject constructor() {

    suspend fun signInWithEmailAndPassword(email: String, password: String): RemoteDbRespond {
        var remoteDbRespond = RemoteDbRespond()
        try {
            Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    remoteDbRespond = remoteDbRespond.copy(isSuccessful = true)
                    Log.d("Login", "Success")
                } else {
                    remoteDbRespond = remoteDbRespond.copy(isSuccessful = false)
                }
            }.await()
        } catch (e: Exception) {
            remoteDbRespond = remoteDbRespond.copy(
                isSuccessful = false,
                errorMessage = e.localizedMessage ?: "An unexpected error occurred"
            )
        }
        return remoteDbRespond
    }

    suspend fun registerWithEmailAndPassword(email: String, password: String): RemoteDbRespond {
        var remoteDbRespond = RemoteDbRespond()
        try {
            Firebase.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    remoteDbRespond = remoteDbRespond.copy(isSuccessful = true)
                    Log.d("Login", "Success")
                } else {
                    remoteDbRespond = remoteDbRespond.copy(isSuccessful = false)
                }
            }.await()
        } catch (e: Exception) {
            remoteDbRespond = remoteDbRespond.copy(
                isSuccessful = false,
                errorMessage = e.localizedMessage ?: "An unexpected error occurred"
            )
            Log.d("Registration", "RemoteDb ${e.message}")
        }
        return remoteDbRespond
    }
}