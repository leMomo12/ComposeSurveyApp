package com.mnowo.composesurveyapp.core.util

object Constants {
    const val DATABASE_NAME = "survey_database"

    const val SURVEY_TITLE_AND_DESCRIPTION_TABLE = "survey_title_and_description_table"
    const val SURVEY_QUESTION_TABLE = "survey_question_table"
    const val SURVEY_ANSWER_TABLE = "survey_answer_table"
    const val SURVEY_CACHING_ANSWER_TABLE = "survey_caching_answer_table"
    const val SURVEY_CACHING_OWN_SURVEY_TABLE = "survey_caching_own_survey"

    const val SURVEY_INFO = "survey_info###?H"
    const val SURVEY_INFO_DETAILS = "SurveyInfoDetails"
    const val DETAILS_DOCUMENT = "Survey details"
    const val SURVEY_QUESTION_ANSWER = "SurveyQuestionAnswers"
    const val SURVEY_QUESTIONS = "SurveyQuestions"


    const val UNEXPECTED_ERROR = "Unexpected error occurred"

    const val MAX_TITLE_LENGTH = 40
    const val MAX_DESCRIPTION_LENGTH = 50
    const val MAX_QUESTION_LENGTH = 30

    const val PARAM_SURVEY_PATH = "surveyPath"
    const val PARAM_SURVEY_INFO = "surveyInfo"
}