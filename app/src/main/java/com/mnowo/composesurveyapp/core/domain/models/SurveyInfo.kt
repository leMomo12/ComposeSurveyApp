package com.mnowo.composesurveyapp.core.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class SurveyInfo(
    val title: String,
    val description: String,
    val questionCount: Int,
    val likes: Int,
    val dislikes: Int,
    val errorMessage: String? = null
) : Parcelable
