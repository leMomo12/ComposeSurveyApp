package com.mnowo.composesurveyapp.feature_answer.data.remote

import android.util.Log.d
import androidx.compose.ui.res.stringResource
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
            Firebase.firestore.collection(collectionPath).whereEqualTo("type", "question").get()
                .addOnSuccessListener {
                    for (document in it.documents) {
                        val questionTitle = document.getString("questionTitle")
                        val question1 = document.getString("questionOne")
                        val question2 = document.getString("questionTwo")
                        val question3 = document.getString("questionThree")
                        val question4 = document.getString("questionFour")
                        val id = document.getField<Int>("id")

                        val getQuestion = GetQuestion(
                            id!!,
                            questionTitle!!,
                            question1,
                            question2,
                            question3,
                            question4
                        )

                        surveyListData.add(getQuestion)
                    }

                }.addOnFailureListener {
                    val getQuestion = GetQuestion(
                        0,
                        "",
                        "",
                        "",
                        "",
                        "",
                        it.localizedMessage ?: "An unexpected error occurred"
                    )

                    surveyListData.add(getQuestion)
                }.await()
        } catch (e: Exception) {
            val getQuestion = GetQuestion(
                0,
                "",
                "",
                "",
                "",
                "",
                e.localizedMessage ?: "An unexpected error occurred"
            )

            surveyListData.add(getQuestion)
        }
        return surveyListData
    }
}