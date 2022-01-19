package com.mnowo.composesurveyapp.core.domain.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class SurveyDetails(
    var likes: Int,
    var dislikes: Int,
    var questionCount: Int,
    @ServerTimestamp var timeStamp: Date? = null
)
