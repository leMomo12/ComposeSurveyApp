package com.mnowo.composesurveyapp.feature_answer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mnowo.composesurveyapp.core.util.Constants
import com.mnowo.composesurveyapp.feature_answer.domain.models.Answer
import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion

@Dao
interface AnswerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cachingSurveyQuestions(getQuestion: GetQuestion)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cachingAnswer(answer: Answer)

    @Query("SELECT * FROM ${Constants.SURVEY_ANSWER_TABLE} ")
    suspend fun getCachedQuestion(): List<GetQuestion>

    @Query("DELETE FROM ${Constants.SURVEY_CACHING_ANSWER_TABLE}")
    suspend fun deleteAllCachedAnswers()

    @Query("DELETE  FROM ${Constants.SURVEY_ANSWER_TABLE}")
    suspend fun deleteAllCachedQuestions()
}