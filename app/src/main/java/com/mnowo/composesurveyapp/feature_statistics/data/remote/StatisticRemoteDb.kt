package com.mnowo.composesurveyapp.feature_statistics.data.remote

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.mnowo.composesurveyapp.core.domain.models.SavingOwnSurvey
import com.mnowo.composesurveyapp.core.util.Constants
import com.mnowo.composesurveyapp.core.domain.models.SurveyInfo
import com.mnowo.composesurveyapp.core.util.UiText
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StatisticRemoteDb @Inject constructor() {

    suspend fun getOwnSurveys(collectionList: List<SavingOwnSurvey>) : MutableList<SurveyInfo> {
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

}