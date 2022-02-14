package com.mnowo.composesurveyapp.feature_add_survey.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mnowo.composesurveyapp.core.domain.models.SavingOwnSurvey
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyQuestion
import com.mnowo.composesurveyapp.feature_add_survey.domain.models.SurveyTitleAndDescription

@Dao
interface AddSurveyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSurveyTitleAndDescriptionToRoom(surveyTitleAndDescription: SurveyTitleAndDescription)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuestion(surveyQuestion: SurveyQuestion)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savingOwnSurvey(savingOwnSurvey: SavingOwnSurvey)

    @Query("SELECT * FROM survey_title_and_description_table")
    suspend fun getTitleAndDescription() : SurveyTitleAndDescription

    @Query("SELECT * FROM survey_question_table")
    suspend fun getSurveyQuestions() : List<SurveyQuestion>

    @Query("DELETE FROM survey_question_table")
    suspend fun deleteAllQuestions()
}