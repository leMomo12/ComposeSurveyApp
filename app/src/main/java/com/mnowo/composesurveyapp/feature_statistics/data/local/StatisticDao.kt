package com.mnowo.composesurveyapp.feature_statistics.data.local

import androidx.room.Dao
import androidx.room.Query
import com.mnowo.composesurveyapp.core.domain.models.SavingOwnSurvey

@Dao
interface StatisticDao {

    @Query("SELECT * FROM survey_caching_own_survey")
    suspend fun getOwnCachedSurveys(): List<SavingOwnSurvey>
}