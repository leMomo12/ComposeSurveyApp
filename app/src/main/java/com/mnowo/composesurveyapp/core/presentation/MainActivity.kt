package com.mnowo.composesurveyapp.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.mnowo.composesurveyapp.core.presentation.components.Navigation
import com.mnowo.composesurveyapp.core.ui.theme.ComposeSurveyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSurveyAppTheme {
                val navController = rememberAnimatedNavController()
                Navigation(navController = navController)
            }
        }
    }
}

