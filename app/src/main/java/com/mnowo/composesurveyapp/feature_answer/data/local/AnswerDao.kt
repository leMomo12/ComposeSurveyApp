package com.mnowo.composesurveyapp.feature_answer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mnowo.composesurveyapp.core.util.Constants
import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion

@Dao
interface AnswerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun cachingSurveyQuestions(getQuestion: GetQuestion)

    @Query("SELECT * FROM ${Constants.SURVEY_ANSWER_TABLE} WHERE id = :id ")
    suspend fun getCachedQuestion(id: Int): GetQuestion

    @Query("DELETE  FROM ${Constants.SURVEY_ANSWER_TABLE}")
    suspend fun deleteAllCachedQuestions()
}