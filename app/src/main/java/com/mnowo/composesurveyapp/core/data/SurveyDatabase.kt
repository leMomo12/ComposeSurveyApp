package com.mnowo.composesurveyapp.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mnowo.composesurveyapp.core.domain.models.SavingOwnSurvey
import com.mnowo.composesurveyapp.feature_add_survey.data.local.AddSurveyDao
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyQuestion
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyTitleAndDescription
import com.mnowo.composesurveyapp.feature_answer.data.local.AnswerDao
import com.mnowo.composesurveyapp.feature_answer.domain.models.Answer
import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion
import com.mnowo.composesurveyapp.feature_auth.data.local.AuthDao
import com.mnowo.composesurveyapp.feature_statistics.data.local.StatisticDao

@Database(
    entities = [SurveyTitleAndDescription::class,
        SurveyQuestion::class,
        GetQuestion::class,
        Answer::class,
        SavingOwnSurvey::class],
    version = 11,
    exportSchema = false
)
abstract class SurveyDatabase : RoomDatabase() {
    abstract fun addSurveyDao(): AddSurveyDao
    abstract fun authDao(): AuthDao
    abstract fun answerDao(): AnswerDao
    abstract fun statisticDao(): StatisticDao
}