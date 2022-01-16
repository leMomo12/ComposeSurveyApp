package com.mnowo.composesurveyapp.feature_answer.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.mnowo.composesurveyapp.core.util.Constants

@Entity(tableName = Constants.SURVEY_CACHING_ANSWER_TABLE)
@TypeConverters(Answer.AnswerOptionsConverter::class)
data class Answer(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var surveyTitle: String,
    var questionTitle: String,
    var answer: AnswerOptions
) {
    enum class AnswerOptions(val value: Int) {
        NULL(0),
        FIRST(1),
        SECOND(2),
        THIRD(3),
        FOURTH(4)
    }

    class AnswerOptionsConverter {
        @TypeConverter
        fun fromAnswerOption(value: AnswerOptions): Int {
            return value.ordinal
        }

        @TypeConverter
        fun toAnswerOption(value: Int): AnswerOptions {
            return when(value) {
                1 -> AnswerOptions.FIRST
                2 -> AnswerOptions.SECOND
                3 -> AnswerOptions.THIRD
                4 -> AnswerOptions.FOURTH
                else -> AnswerOptions.NULL
            }
        }
    }
}
