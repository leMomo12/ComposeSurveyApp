package com.mnowo.composesurveyapp.core.presentation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.mnowo.composesurveyapp.core.util.Screen
import com.mnowo.composesurveyapp.feature_auth.presentation.login.LoginScreen
import com.google.accompanist.navigation.animation.navigation
import com.mnowo.composesurveyapp.feature_add_survey.NewSurveyScreen
import com.mnowo.composesurveyapp.feature_add_survey.presentation.add_survey_question.AddSurveyQuestionScreen
import com.mnowo.composesurveyapp.feature_auth.presentation.register.RegisterScreen
import com.mnowo.composesurveyapp.feature_auth.presentation.splash.SplashScreen
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
            LoginScreen(navController, onNavigate = navController::navigate)
        }

        composable(Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController, onNavigate = navController::navigate)
        }

        composable(Screen.HomeScreen.route) {
            HomeScreen(navController, onNavigate = navController::navigate)
        }

        composable(Screen.NewSurveyScreen.route) {
            NewSurveyScreen(navController, onNavigate = navController::navigate)
        }

        composable(Screen.AddSurveyQuestionScreen.route) {
            AddSurveyQuestionScreen(navController = navController)
        }
    }
}

