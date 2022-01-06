package com.mnowo.composesurveyapp.core.presentation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.mnowo.composesurveyapp.core.util.Constants
import com.mnowo.composesurveyapp.core.util.Screen
import com.mnowo.composesurveyapp.feature_add_survey.NewSurveyScreen
import com.mnowo.composesurveyapp.feature_add_survey.presentation.add_survey_question.AddSurveyQuestionScreen
import com.mnowo.composesurveyapp.feature_add_survey.presentation.done_screen.DoneScreen
import com.mnowo.composesurveyapp.feature_answer.presentation.after_answer_screen.AfterAnswerScreen
import com.mnowo.composesurveyapp.feature_answer.presentation.answer_screen.AnswerScreen
import com.mnowo.composesurveyapp.feature_answer.presentation.before_answer_screen.BeforeAnswerScreen
import com.mnowo.composesurveyapp.feature_auth.presentation.login.LoginScreen
import com.mnowo.composesurveyapp.feature_auth.presentation.register.RegisterScreen
import com.mnowo.composesurveyapp.feature_auth.presentation.splash.SplashScreen
import com.mnowo.composesurveyapp.feature_home.domain.models.SurveyInfo
import com.mnowo.composesurveyapp.feature_home.presentation.home.HomeScreen

@ExperimentalAnimationApi
@Composable
fun Navigation(navController: NavHostController) {
    AnimatedNavHost(navController = navController, startDestination = Screen.SplashScreen.route) {

        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController)
        }

        composable(
            Screen.LoginScreen.route,
            enterTransition = { _, _ ->
                fadeIn(animationSpec = tween(2000))
            },
            exitTransition = { _, _ ->
                fadeOut(animationSpec = tween(2000))
            }
        ) {
            LoginScreen(onNavigate = navController::navigate)
        }

        composable(Screen.RegisterScreen.route) {
            RegisterScreen(onNavigate = navController::navigate)
        }

        composable(Screen.HomeScreen.route) {
            HomeScreen(onNavigate = navController::navigate, navController = navController)
        }

        composable(Screen.NewSurveyScreen.route) {
            NewSurveyScreen(onNavigate = navController::navigate)
        }

        composable(Screen.AddSurveyQuestionScreen.route) {
            AddSurveyQuestionScreen(onNavigate = navController::navigate)
        }

        composable(Screen.DoneScreen.route) {
            DoneScreen(onNavigate = navController::navigate)
        }

        composable(Screen.BeforeAnswerScreen.route) {

            val surveyInfo = navController.previousBackStackEntry?.savedStateHandle?.get<SurveyInfo>(Constants.PARAM_SURVEY_INFO)

            surveyInfo?.let {
                BeforeAnswerScreen(onNavigate = navController::navigate, surveyInfo = it)
            }
        }

        composable(Screen.AnswerScreen.route + "/{surveyPath}") {
            AnswerScreen(onNavigate = navController::navigate, navController = navController)
        }

        composable(Screen.AfterAnswerScreen.route) {
            AfterAnswerScreen(onNavigate = navController::navigate)
        }
    }
}

