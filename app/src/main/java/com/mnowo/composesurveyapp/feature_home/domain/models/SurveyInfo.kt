package com.mnowo.composesurveyapp.feature_home.domain.models

data class SurveyInfo(
    val title: String,
    val description: String,
    val questionCount: Int,
    val likes: Int,
    val dislikes: Int,
    val errorMessage: String? = null
)
