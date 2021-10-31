package com.mnowo.composesurveyapp.feature_add_survey.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyTitleAndDescription

@Dao
interface AddSurveyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSurveyTitleAndDescription(surveyTitleAndDescription: SurveyTitleAndDescription)
}