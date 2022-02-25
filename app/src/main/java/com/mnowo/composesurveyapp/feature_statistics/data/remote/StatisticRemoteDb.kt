package com.mnowo.composesurveyapp.feature_statistics.data.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.mnowo.composesurveyapp.core.domain.models.SavingOwnSurvey
import com.mnowo.composesurveyapp.core.util.Constants
import com.mnowo.composesurveyapp.core.domain.models.SurveyInfo
import com.mnowo.composesurveyapp.core.util.UiText
import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StatisticRemoteDb @Inject constructor() {

    suspend fun getOwnSurveys(collectionList: List<SavingOwnSurvey>): MutableList<SurveyInfo> {
        val surveyInfoList: MutableList<SurveyInfo> = mutableListOf()

        try {
            for (path in collectionList) {
                Firebase.firestore.collection(Constants.SURVEY_INFO).document(path.title)
                    .collection(Constants.SURVEY_INFO_DETAILS).document(Constants.DETAILS_DOCUMENT)
                    .get()
                    .addOnSuccessListener { details ->
                        val questionCount = details.getField<Int>("questionCount")
                        val likes = details.getField<Int>("likes")
                        val dislikes = details.getField<Int>("dislikes")

                        val surveyInfo = SurveyInfo(
                            path.title!!,
                            path.description!!,
                            questionCount!!,
                            likes!!,
                            dislikes!!
                        )
                        surveyInfoList.add(surveyInfo)
                    }.addOnFailureListener {
                        surveyInfoList.add(
                            SurveyInfo(
                                "",
                                "",
                                0,
                                0,
                                0,
                                it.localizedMessage ?: UiText.unknownError().toString()
                            )
                        )
                    }.await()
            }
        } catch (e: Exception) {
            surveyInfoList.add(
                SurveyInfo(
                    "",
                    "",
                    0,
                    0,
                    0,
                    e.localizedMessage ?: UiText.unknownError().toString()
                )
            )
        }
        return surveyInfoList
    }

    suspend fun getAnswer(collectionPath: String): List<GetQuestion> {
        val surveyListData: MutableList<GetQuestion> = mutableListOf()

        try {
            val result = FirebaseFirestore.getInstance().collection(Constants.SURVEY_INFO)
                .document(collectionPath)
                .collection(Constants.SURVEY_QUESTIONS)
                .get()
                .await()

            val answerResult = FirebaseFirestore.getInstance().collection(Constants.SURVEY_INFO)
                .document(collectionPath)
                .collection(Constants.SURVEY_QUESTION_ANSWER)
                .get()
                .await()

            for (document in result.documents) {
                val questionTitle = document.getString("questionTitle")
                for (answer in answerResult.documents) {
                    if (answer.toString() == questionTitle) {
                        val firstQuestion = answer.getField<Int>("firstQuestion")
                        val secondQuestion = answer.getField<Int>("secondQuestion")
                        val thirdQuestion = answer.getField<Int>("thirdQuestion")
                        val fourthQuestion = answer.getField<Int>("fourthQuestion")

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
                            questionFour,
                            countOne = firstQuestion,
                            countTwo = secondQuestion,
                            countThree = thirdQuestion,
                            countFour = fourthQuestion
                        )

                        surveyListData.add(getQuestion)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("getSurvey", "Exception: ${e.localizedMessage} , ${e.cause}")

        }
        return surveyListData
    }

}