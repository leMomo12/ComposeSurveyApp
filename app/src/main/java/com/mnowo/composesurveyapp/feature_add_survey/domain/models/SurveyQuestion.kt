package com.mnowo.composesurveyapp.feature_add_survey.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mnowo.composesurveyapp.core.util.Constants

@Entity(tableName = Constants.SURVEY_QUESTION_TABLE)
data class SurveyQuestion(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var questionTitle: String,
    var questionOne: String?,
    var questionTwo: String?,
    var questionThree: String?,
    var questionFour: String?
)
