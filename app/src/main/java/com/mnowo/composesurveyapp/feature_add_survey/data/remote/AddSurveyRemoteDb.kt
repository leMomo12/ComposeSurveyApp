package com.mnowo.composesurveyapp.feature_add_survey.data.remote

import android.util.Log.d
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyQuestion
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyTitleAndDescription
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AddSurveyRemoteDb @Inject constructor(){

    suspend fun addSurveyQuestions(surveyQuestion: SurveyQuestion, surveyTitleAndDescription: SurveyTitleAndDescription) {
        try {
            Firebase.firestore.collection(surveyTitleAndDescription.title).add(surveyQuestion).addOnSuccessListener {
                d("AddSurvey", "Successfully added survey")
            }.addOnFailureListener {
                d("AddSurvey", "Failed added survey $it")
            }.await()
        } catch (e: Exception) {
            d("AddSurvey", "Failed added survey: ${e.localizedMessage}")
        }
    }

    suspend fun addSurveyTitleAndDescription(surveyTitleAndDescription: SurveyTitleAndDescription) {
        try {
            Firebase.firestore.collection(surveyTitleAndDescription.title).add(surveyTitleAndDescription).addOnSuccessListener {

            }.addOnFailureListener {

            }.await()
        }catch (e: Exception) {

        }
    }
}