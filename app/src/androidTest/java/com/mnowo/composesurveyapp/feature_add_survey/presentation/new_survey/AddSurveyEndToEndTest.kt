package com.mnowo.composesurveyapp.feature_add_survey.presentation.new_survey

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
import com.mnowo.composesurveyapp.di.AddSurveyModule
import com.mnowo.composesurveyapp.di.AuthModule
import com.mnowo.composesurveyapp.feature_add_survey.NewSurveyScreen
import com.mnowo.composesurveyapp.feature_add_survey.presentation.add_survey_question.AddSurveyQuestionScreen
import com.mnowo.composesurveyapp.feature_add_survey.presentation.done_screen.DoneScreen
import com.mnowo.composesurveyapp.feature_home.presentation.home.HomeScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AuthModule::class)
class AddSurveyEndToEndTest {

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
                NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
                    composable(Screen.HomeScreen.route) {
                        HomeScreen(onNavigate = navController::navigate)
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
                }
            }
        }
    }

    @ExperimentalAnimationApi
    @Test
    fun from_homeScreen_to_newSurveyScreen_and_back() {
        composeRule.onNodeWithTag(TestTags.HOME_TO_NEW_SURVEY_BUTTON).performClick()
        composeRule.onNodeWithTag(TestTags.NEW_SURVEY_TITLE).assertExists()
        composeRule.onNodeWithTag(TestTags.NEW_SURVEY_TO_HOME_BUTTON).performClick()

        composeRule.onNodeWithTag(TestTags.HOME_TO_NEW_SURVEY_BUTTON).assertExists()
    }

    @ExperimentalAnimationApi
    @Test
    fun new_survey_with_valid_input() {
        composeRule.onNodeWithTag(TestTags.HOME_TO_NEW_SURVEY_BUTTON).performClick()

        composeRule.onNodeWithTag(TestTags.NEW_SURVEY_TITLE).performTextInput("title")
        composeRule.onNodeWithTag(TestTags.NEW_SURVEY_DESCRIPTION).performTextInput("description")

        composeRule.onNodeWithTag(TestTags.NEW_SURVEY_TITLE).assert(hasText("title"))
        composeRule.onNodeWithTag(TestTags.NEW_SURVEY_DESCRIPTION).assert(hasText("description"))

        composeRule.onNodeWithTag(TestTags.NEW_SURVEY_BUTTON).performClick()

        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_TITLE).assertIsDisplayed()
    }

    @ExperimentalAnimationApi
    @Test
    fun add_survey_question_input_check() {
        composeRule.onNodeWithTag(TestTags.HOME_TO_NEW_SURVEY_BUTTON).performClick()

        composeRule.onNodeWithTag(TestTags.NEW_SURVEY_TITLE).performTextInput("title")
        composeRule.onNodeWithTag(TestTags.NEW_SURVEY_DESCRIPTION).performTextInput("description")

        composeRule.onNodeWithTag(TestTags.NEW_SURVEY_TITLE).assert(hasText("title"))
        composeRule.onNodeWithTag(TestTags.NEW_SURVEY_DESCRIPTION).assert(hasText("description"))

        composeRule.onNodeWithTag(TestTags.NEW_SURVEY_BUTTON).performClick()

        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_TITLE).performTextInput("questionTitle")
        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_ONE).performTextInput("one")
        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_TWO).performTextInput("two")
        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_THREE).performTextInput("three")
        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_FOUR).performTextInput("four")

        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_TITLE).assert(hasText("questionTitle"))
        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_ONE).assert(hasText("one"))
        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_TWO).assert(hasText("two"))
        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_THREE).assert(hasText("three"))
        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_FOUR).assert(hasText("four"))
    }

    @ExperimentalAnimationApi
    @Test
    fun add_survey_question_add_question() {
        composeRule.onNodeWithTag(TestTags.HOME_TO_NEW_SURVEY_BUTTON).performClick()

        composeRule.onNodeWithTag(TestTags.NEW_SURVEY_TITLE).performTextInput("title")
        composeRule.onNodeWithTag(TestTags.NEW_SURVEY_DESCRIPTION).performTextInput("description")

        composeRule.onNodeWithTag(TestTags.NEW_SURVEY_TITLE).assert(hasText("title"))
        composeRule.onNodeWithTag(TestTags.NEW_SURVEY_DESCRIPTION).assert(hasText("description"))

        composeRule.onNodeWithTag(TestTags.NEW_SURVEY_BUTTON).performClick()

        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_TITLE).performTextInput("questionTitle")
        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_ONE).performTextInput("one")
        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_TWO).performTextInput("two")
        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_THREE).performTextInput("three")
        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_FOUR).performTextInput("four")

        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_TITLE).assert(hasText("questionTitle"))
        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_ONE).assert(hasText("one"))
        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_TWO).assert(hasText("two"))
        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_THREE).assert(hasText("three"))
        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_FOUR).assert(hasText("four"))

        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_ADD_QUESTION_BUTTON).performClick()

        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_TITLE).assert(hasText(""))
        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_ONE).assert(hasText(""))
        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_TWO).assert(hasText(""))
        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_THREE).assert(hasText(""))
        composeRule.onNodeWithTag(TestTags.ADD_SURVEY_QUESTION_FOUR).assert(hasText(""))
    }
}