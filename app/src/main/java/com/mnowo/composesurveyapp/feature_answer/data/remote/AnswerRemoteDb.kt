package com.mnowo.composesurveyapp.feature_answer.data.remote

import android.util.Log.d
import androidx.compose.ui.res.stringResource
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.mnowo.composesurveyapp.R
import com.mnowo.composesurveyapp.core.domain.models.RemoteDbRespond
import com.mnowo.composesurveyapp.core.util.Constants
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyQuestion
import com.mnowo.composesurveyapp.feature_answer.data.remote.util.UserAnswerUtil
import com.mnowo.composesurveyapp.feature_answer.domain.models.Answer
import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AnswerRemoteDb @Inject constructor() {

    suspend fun getSurvey(collectionPath: String): List<GetQuestion> {
        d("getSurvey", "collectionPath: $collectionPath")
        val surveyListData: MutableList<GetQuestion> = mutableListOf()

        try {
            val result = FirebaseFirestore.getInstance().collection(Constants.SURVEY_INFO)
                .document(collectionPath)
                .collection(Constants.SURVEY_QUESTIONS)
                .get()
                .await()

            for (document in result.documents) {
                d("remote", "heere")
                val questionTitle = document.getString("questionTitle")
                val questionOne = document.getString("questionOne")
                val questionTwo = document.getString("questionTwo")
                val questionThree = document.getString("questionThree")
                val questionFour = document.getString("questionFour")

                val getQuestion = GetQuestion(
                    0,
                    questionTitle!!,
                    questionOne,
                    questionTwo,
                    questionThree,
                    questionFour
                )

                surveyListData.add(getQuestion)
            }
        } catch (e: Exception) {
            d("getSurvey", "Exception: ${e.localizedMessage} , ${e.cause}")

        }
        return surveyListData
    }

    suspend fun addUserAnswer(answer: Answer): RemoteDbRespond {
        var dbRespond = RemoteDbRespond()
        try {
            val questionAnswer = UserAnswerUtil.getUserAnswer(answer = answer)
            Firebase.firestore.collection(Constants.SURVEY_INFO).document(answer.surveyTitle)
                .collection(Constants.SURVEY_QUESTION_ANSWER).document(answer.questionTitle)
                .update(questionAnswer, FieldValue.increment(1)).addOnSuccessListener {
                    dbRespond = dbRespond.copy(isSuccessful = true)
                }.addOnFailureListener {
                    dbRespond = dbRespond.copy(
                        errorMessage = it.localizedMessage ?: "An unexpected error occurred"
                    )
                }.await()
        } catch (e: Exception) {
            dbRespond =
                dbRespond.copy(errorMessage = e.localizedMessage ?: "An unexpected error occurred")
        }
        return dbRespond
    }

    suspend fun addSurveyRating(
        thumbUp: Boolean,
        surveyName: String
    ): RemoteDbRespond {
        var remoteDbRespond = RemoteDbRespond()
        try {
            Firebase.firestore.collection(Constants.SURVEY_INFO).document(surveyName)
                .collection(Constants.SURVEY_INFO_DETAILS).document(Constants.DETAILS_DOCUMENT)
                .update(if (thumbUp) "likes" else "dislikes", FieldValue.increment(1))
                .addOnSuccessListener {
                    remoteDbRespond = remoteDbRespond.copy(
                        isSuccessful = true
                    )
                }.addOnFailureListener {
                    remoteDbRespond = remoteDbRespond.copy(
                        isSuccessful = false,
                        errorMessage = it.localizedMessage
                    )
                }.await()
        } catch (e: Exception) {
            remoteDbRespond = remoteDbRespond.copy(
                isSuccessful = false,
                errorMessage = e.localizedMessage
            )
        }
        return remoteDbRespond
    }
}