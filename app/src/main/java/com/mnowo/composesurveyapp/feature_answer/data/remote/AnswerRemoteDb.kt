package com.mnowo.composesurveyapp.feature_answer.data.remote

import android.util.Log.d
import androidx.compose.ui.res.stringResource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.mnowo.composesurveyapp.R
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyQuestion
import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AnswerRemoteDb @Inject constructor() {

    suspend fun getSurvey(collectionPath: String) : List<GetQuestion> {
        d("getSurvey", "collectionPath: $collectionPath")
        val surveyListData: MutableList<GetQuestion> = mutableListOf()

        try {
            val result = FirebaseFirestore.getInstance().collection(collectionPath)
                .whereEqualTo("type", "question")
                .get()
                .await()

            for(document in result.documents) {
                d("remote", "heere")
                val questionTitle = document.getString("questionTitle")
                val questionOne = document.getString("questionOne")
                val questionTwo = document.getString("questionTwo")
                val questionThree = document.getString("questionThree")
                val questionFour = document.getString("questionFour")

                val getQuestion = GetQuestion(0, questionTitle!!, questionOne, questionTwo, questionThree, questionFour)

                surveyListData.add(getQuestion)
            }
        } catch (e: Exception) {
            d("getSurvey", "Exception: ${e.localizedMessage} , ${e.cause}")

        }
        return surveyListData
    }
}