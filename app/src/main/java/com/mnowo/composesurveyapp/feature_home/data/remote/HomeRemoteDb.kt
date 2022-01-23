package com.mnowo.composesurveyapp.feature_home.data.remote

import android.util.Log.d
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.mnowo.composesurveyapp.core.util.Constants
import com.mnowo.composesurveyapp.feature_home.domain.models.SurveyInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HomeRemoteDb @Inject constructor() {

    suspend fun getSurveyInfo(): MutableList<SurveyInfo> {
        val surveyInfoList: MutableList<SurveyInfo> = mutableListOf()
        try {
            val result =
                FirebaseFirestore.getInstance().collection(Constants.SURVEY_INFO).get().await()

            if (!result.isEmpty) {
                for (document in result.documents) {
                    val title = document.getString("title")
                    val description = document.getString("description")

                    FirebaseFirestore.getInstance().collection(Constants.SURVEY_INFO)
                        .document(title.toString())
                        .collection(Constants.SURVEY_INFO_DETAILS)
                        .document(Constants.DETAILS_DOCUMENT).get()
                        .addOnSuccessListener { details ->
                            d("getSurvey", "in here")
                            val questionCount = details.getField<Int>("questionCount")
                            val likes = details.getField<Int>("likes")
                            val dislikes = details.getField<Int>("dislikes")

                            val surveyInfo = SurveyInfo(
                                title!!,
                                description!!,
                                questionCount!!,
                                likes!!,
                                dislikes!!
                            )
                            surveyInfoList.add(surveyInfo)
                            d("getSurvey", "Data: ${surveyInfoList.toString()}")
                        }.await()
                }
            }
        } catch (e: Exception) {
            d("getSurvey", e.localizedMessage)
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