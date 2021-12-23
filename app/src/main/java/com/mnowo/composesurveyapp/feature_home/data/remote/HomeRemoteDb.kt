package com.mnowo.composesurveyapp.feature_home.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mnowo.composesurveyapp.core.util.Constants
import com.mnowo.composesurveyapp.feature_home.domain.models.SurveyInfo
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HomeRemoteDb @Inject constructor() {

    suspend fun getSurveyInfo(): MutableList<SurveyInfo> {
        var surveyInfoList: MutableList<SurveyInfo> = mutableListOf()

        try {
            FirebaseFirestore.getInstance().collection(Constants.SURVEY_INFO).get()
                .addOnSuccessListener {
                    for (document in it.documents) {
                        val title = document.getString("title")
                        val description = document.getString("description")
                        val questionCount = 14
                        val likes = 42
                        val dislikes = 4

                        val surveyInfo =
                            SurveyInfo(title!!, description!!, questionCount, likes, dislikes)
                        surveyInfoList.add(surveyInfo)
                    }
                }.addOnFailureListener {
                    val surveyInfo = SurveyInfo(
                        "",
                        "",
                        0,
                        0,
                        0,
                        it.localizedMessage ?: Constants.UNEXPECTED_ERROR
                    )
                    surveyInfoList.add(surveyInfo)
                }.await()
            return surveyInfoList
        } catch (e: Exception) {
            val surveyInfo = SurveyInfo(
                "",
                "",
                0,
                0,
                0,
                e.localizedMessage ?: Constants.UNEXPECTED_ERROR
            )
            surveyInfoList.add(surveyInfo)
        }

        return surveyInfoList
    }
}