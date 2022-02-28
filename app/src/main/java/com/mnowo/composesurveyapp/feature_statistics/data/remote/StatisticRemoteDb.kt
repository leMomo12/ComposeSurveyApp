package com.mnowo.composesurveyapp.feature_statistics.data.remote

import android.util.Log
import android.util.Log.d
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
            Firebase.runCatching {
                val result = FirebaseFirestore.getInstance().collection(Constants.SURVEY_INFO)
                    .document(collectionPath)
                    .collection(Constants.SURVEY_QUESTIONS)
                    .get()
                    .await()

                for (document in result.documents) {

                    val questionTitle = document.getString("questionTitle")

                    val answerResult = questionTitle?.let {
                        FirebaseFirestore.getInstance().collection(Constants.SURVEY_INFO)
                            .document(collectionPath)
                            .collection(Constants.SURVEY_QUESTION_ANSWER)
                            .document(it)
                            .get()
                            .await()
                    }

                    val firstQuestion = answerResult?.getField<Int>("firstQuestion")
                    val secondQuestion = answerResult?.getField<Int>("secondQuestion")
                    val thirdQuestion = answerResult?.getField<Int>("thirdQuestion")
                    val fourthQuestion = answerResult?.getField<Int>("fourthQuestion")


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

                    d("stats", "listData: ${getQuestion.toString()}")
                    surveyListData.add(getQuestion)
                }
            }.onSuccess {
                d("stats", "firebase success")
            }.onFailure {
                d("stats", "firebase failed")
            }

        } catch (e: Exception) {
            Log.d("stats", "Exception: ${e.localizedMessage} , ${e.cause}")

        }
        return surveyListData
    }

}