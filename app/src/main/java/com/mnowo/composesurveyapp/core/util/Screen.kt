package com.mnowo.composesurveyapp.core.util

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object HomeScreen : Screen("home_screen")
    object NewSurveyScreen : Screen("new_survey_screen")
    object AddSurveyQuestionScreen : Screen("survey_question_screen")
    object DoneScreen : Screen("done_screen")
    object BeforeAnswerScreen : Screen("before_answer_screen")
    object AnswerScreen : Screen("answer_screen")
    object AfterAnswerScreen : Screen("after_answer_screen")
}
