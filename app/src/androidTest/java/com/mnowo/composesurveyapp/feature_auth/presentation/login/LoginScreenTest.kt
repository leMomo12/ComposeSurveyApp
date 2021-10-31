package com.mnowo.composesurveyapp.feature_auth.presentation.login

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mnowo.composesurveyapp.core.presentation.MainActivity
import com.mnowo.composesurveyapp.core.ui.theme.ComposeSurveyAppTheme
import com.mnowo.composesurveyapp.core.util.Screen
import com.mnowo.composesurveyapp.core.util.TestTags
import com.mnowo.composesurveyapp.di.AppModule
import com.mnowo.composesurveyapp.di.AuthModule
import com.mnowo.composesurveyapp.di.HomeModule
import com.mnowo.composesurveyapp.feature_auth.presentation.register.RegisterScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class, AuthModule::class, HomeModule::class)
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

    @ExperimentalAnimationApi
    @Test
    fun navigateToLogin() {
        composeRule.onNodeWithTag(TestTags.LOGIN_SCREEN_TEXT_BUTTON).performClick()

        composeRule.onNodeWithTag(TestTags.REGISTER_SCREEN_TEXT_BUTTON).assertIsDisplayed()
    }

    @ExperimentalAnimationApi
    @Test
    fun textInput() {
        composeRule.onNodeWithTag(TestTags.LOGIN_EMAIL).performTextInput("a@a.de")
        composeRule.onNodeWithTag(TestTags.LOGIN_PASSWORD).performTextInput("123456")

        composeRule.onNodeWithTag(TestTags.LOGIN_EMAIL).assert(hasText("a@a.de"))
        composeRule.onNodeWithTag(TestTags.LOGIN_PASSWORD).assert(hasText("123456"))
    }

    @ExperimentalAnimationApi
    @Test
    fun performRegisterButton() {
        composeRule.onNodeWithTag(TestTags.LOGIN_EMAIL).performTextInput("a@a.de")
        composeRule.onNodeWithTag(TestTags.LOGIN_PASSWORD).performTextInput("123456")
        composeRule.onNodeWithTag(TestTags.LOGIN_BUTTON).performClick()

        composeRule.onNodeWithTag(TestTags.LOGIN_BUTTON).assertIsNotEnabled()
        composeRule.onNodeWithTag(TestTags.LOGIN_SCREEN_TEXT_BUTTON).assertIsNotEnabled()
        composeRule.onNodeWithTag(TestTags.LOGIN_EMAIL).assertIsNotEnabled()
        composeRule.onNodeWithTag(TestTags.LOGIN_PASSWORD).assertIsNotEnabled()
        composeRule.onNodeWithTag(TestTags.LOGIN_PROGRESSBAR).assertIsDisplayed()
    }

    @ExperimentalAnimationApi
    @Test
    fun performRegisterButtonWithInvalidInputs() {

    }
}