package com.mnowo.composesurveyapp.feature_auth.presentation.login

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mnowo.composesurveyapp.core.presentation.MainActivity
import com.mnowo.composesurveyapp.core.ui.theme.ComposeSurveyAppTheme
import com.mnowo.composesurveyapp.core.util.Screen
import com.mnowo.composesurveyapp.di.AppModule
import com.mnowo.composesurveyapp.di.AuthModule
import com.mnowo.composesurveyapp.feature_auth.presentation.register.RegisterScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

@HiltAndroidTest
@UninstallModules(AppModule::class, AuthModule::class)
class LoginScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @ExperimentalAnimationApi
    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            ComposeSurveyAppTheme {
                NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
                    composable(Screen.LoginScreen.route) {
                        LoginScreen(navController = navController)
                    }
                    composable(Screen.RegisterScreen.route) {
                        RegisterScreen(navController = navController)
                    }
                }
            }
        }
    }
}