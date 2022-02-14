package com.mnowo.composesurveyapp.core.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mnowo.composesurveyapp.core.util.Constants

@Entity(tableName = Constants.SURVEY_CACHING_OWN_SURVEY_TABLE)
data class SavingOwnSurvey(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var description: String
)
