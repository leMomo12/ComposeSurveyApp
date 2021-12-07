package com.mnowo.composesurveyapp.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mnowo.composesurveyapp.feature_add_survey.data.local.AddSurveyDao
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyQuestion
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyTitleAndDescription
import com.mnowo.composesurveyapp.feature_auth.data.local.AuthDao
import javax.inject.Inject

@Database(
    entities = [SurveyTitleAndDescription::class, SurveyQuestion::class],
    version = 3,
    exportSchema = false
)
abstract class SurveyDatabase : RoomDatabase() {
    abstract fun addSurveyDao(): AddSurveyDao
    abstract fun authDao(): AuthDao
}