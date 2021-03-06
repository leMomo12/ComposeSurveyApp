package com.mnowo.composesurveyapp.feature_add_survey.data.remote

import android.util.Log.d
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mnowo.composesurveyapp.core.domain.models.RemoteDbRespond
import com.mnowo.composesurveyapp.core.domain.models.SurveyDetails
import com.mnowo.composesurveyapp.core.util.Constants
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.AnswerCounter
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyQuestion
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyTitleAndDescription
import com.mnowo.composesurveyapp.feature_answer.domain.models.Answer
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AddSurveyRemoteDb @Inject constructor() {

    suspend fun addSurveyQuestions(
        surveyQuestion: SurveyQuestion,
        surveyTitleAndDescription: SurveyTitleAndDescription
    ): RemoteDbRespond {
        var dbRespond = RemoteDbRespond()
        try {
            Firebase.firestore.collection(Constants.SURVEY_INFO)
                .document(surveyTitleAndDescription.title).collection(Constants.SURVEY_QUESTIONS)
                .add(surveyQuestion)
                .addOnSuccessListener {
                    d("AddSurvey", "Successfully added survey")
                    dbRespond = dbRespond.copy(isSuccessful = true)
                }.addOnFailureListener {
                    d("AddSurvey", "Failed added survey $it")
                    dbRespond = dbRespond.copy(
                        isSuccessful = false,
                        errorMessage = it.localizedMessage ?: "An unexpected error occurred"
                    )
                }.await()
            val answerCounter = AnswerCounter(surveyQuestion.id)
            if (dbRespond.isSuccessful == true) {
                Firebase.firestore.collection(Constants.SURVEY_INFO)
                    .document(surveyTitleAndDescription.title)
                    .collection(Constants.SURVEY_QUESTION_ANSWER)
                    .document(surveyQuestion.questionTitle).set(answerCounter)
                    .addOnSuccessListener {
                        dbRespond = dbRespond.copy(isSuccessful = true)
                    }.addOnFailureListener {
                        dbRespond = dbRespond.copy(
                            isSuccessful = false,
                            errorMessage = it.localizedMessage ?: "An unexpected error occurred"
                        )
                    }.await()
            }

        } catch (e: Exception) {
            d("AddSurvey", "Failed added survey: ${e.localizedMessage}")
            dbRespond = dbRespond.copy(
                isSuccessful = false,
                errorMessage = e.localizedMessage ?: "An unexpected error occurred"
            )
        }
        return dbRespond
    }

    suspend fun addSurveyTitleAndDescription(surveyTitleAndDescription: SurveyTitleAndDescription): RemoteDbRespond {
        var dbRespond = RemoteDbRespond()
        try {
            Firebase.firestore.collection(surveyTitleAndDescription.title)
                .add(surveyTitleAndDescription).addOnSuccessListener {
                    dbRespond = dbRespond.copy(isSuccessful = true)
                }.addOnFailureListener {
                    dbRespond = dbRespond.copy(
                        isSuccessful = false,
                        errorMessage = it.localizedMessage ?: "An unexpected error occurred"
                    )
                }.await()


        } catch (e: Exception) {
            dbRespond = dbRespond.copy(
                isSuccessful = false,
                errorMessage = e.localizedMessage ?: "An unexpected error occurred"
            )
        }
        return dbRespond
    }

    // Add to another collection for getting it back more easily
    suspend fun addSurveyTitleAndDescriptionToInfo(
        surveyTitleAndDescription: SurveyTitleAndDescription,
        questionCount: Int,
    ): RemoteDbRespond {
        var dbRespond = RemoteDbRespond()
        try {
            Firebase.firestore.collection(Constants.SURVEY_INFO)
                .document(surveyTitleAndDescription.title).set(surveyTitleAndDescription)
                .addOnSuccessListener {
                    dbRespond = dbRespond.copy(isSuccessful = true)
                }.addOnFailureListener {
                    dbRespond = dbRespond.copy(
                        isSuccessful = false,
                        errorMessage = it.localizedMessage ?: "An unexpected error occurred"
                    )
                }.await()

            val surveyDetails = SurveyDetails(0, 0, questionCount = questionCount)

            if (dbRespond.isSuccessful == true) {
                Firebase.firestore.collection(Constants.SURVEY_INFO)
                    .document(surveyTitleAndDescription.title)
                    .collection(Constants.SURVEY_INFO_DETAILS)
                    .document(Constants.DETAILS_DOCUMENT).set(surveyDetails).addOnSuccessListener {
                        dbRespond = dbRespond.copy(isSuccessful = true)
                    }.addOnFailureListener {
                        dbRespond = dbRespond.copy(
                            isSuccessful = false,
                            errorMessage = it.localizedMessage ?: "An unexpected error occurred"
                        )
                    }.await()
            }
        } catch (e: Exception) {
            dbRespond = dbRespond.copy(
                isSuccessful = false,
                errorMessage = e.localizedMessage ?: "An unexpected error occurred"
            )
        }
        return dbRespond
    }
}