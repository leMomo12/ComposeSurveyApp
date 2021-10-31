package com.mnowo.composesurveyapp.feature_add_survey.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mnowo.composesurveyapp.core.util.Constants

@Entity(tableName = Constants.SURVEY_TITLE_AND_DESCRIPTION_TABLE)
data class SurveyTitleAndDescription(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var description: String
)
