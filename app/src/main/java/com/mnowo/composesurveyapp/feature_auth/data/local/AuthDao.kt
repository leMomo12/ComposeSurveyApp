package com.mnowo.composesurveyapp.feature_auth.data.local

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AuthDao {

    @Query("DELETE FROM survey_question_table")
    suspend fun deleteAllQuestions()
}