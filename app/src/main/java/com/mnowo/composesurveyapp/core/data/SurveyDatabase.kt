package com.mnowo.composesurveyapp.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mnowo.composesurveyapp.feature_add_survey.data.local.AddSurveyDao
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyQuestion
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyTitleAndDescription

@Database(
    entities = [SurveyTitleAndDescription::class, SurveyQuestion::class],
    version = 2,
    exportSchema = false
)
abstract class SurveyDatabase : RoomDatabase() {
    abstract fun addSurveyDao(): AddSurveyDao
}