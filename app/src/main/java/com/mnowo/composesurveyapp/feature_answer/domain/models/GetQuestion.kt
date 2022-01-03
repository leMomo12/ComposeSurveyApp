package com.mnowo.composesurveyapp.feature_answer.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mnowo.composesurveyapp.core.util.Constants

@Entity(tableName = Constants.SURVEY_ANSWER_TABLE)
data class GetQuestion(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var questionTitle: String,
    var questionOne: String?,
    var questionTwo: String?,
    var questionThree: String?,
    var questionFour: String?,
    var error: String? = null
)
