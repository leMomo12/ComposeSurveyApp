package com.mnowo.composesurveyapp.feature_statistics

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.mnowo.composesurveyapp.R
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.feature_statistics.presentation.statistic_screen.StatisticViewModel
import kotlinx.coroutines.flow.collectLatest


data class Point(val X: Float = 0f, val Y: Float = 0f)

@Composable
fun StatisticScreen(onNavigate: (String) -> Unit, viewModel: StatisticViewModel = hiltViewModel()) {
    val scaffoldState = rememberScaffoldState()


    val istokweb = FontFamily(
        Font(R.font.istokweb_bold, FontWeight.Bold),
        Font(R.font.istokweb_bolditalic, FontWeight.ExtraBold),
        Font(R.font.istokweb_italic, FontWeight.Normal),
        Font(R.font.istokweb_regular, FontWeight.Medium),
    )

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest {
            when (it) {
                is UiEvent.Navigate -> {
                    onNavigate(it.route)
                }
                is UiEvent.ShowSnackbar -> {}
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        Text(
            text = "Watch your",
            fontSize = 32.sp,
            fontFamily = istokweb,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 25.dp, start = 20.dp, end = 20.dp)
        )
        Text(
            text = "survey's performing",
            fontSize = 32.sp,
            fontFamily = istokweb,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        )
        Spacer(modifier = Modifier.padding(vertical = 15.dp))

    }
}

@Composable
fun SurveyStatisticsBarChart() {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->

        },
        update = { barChart ->

        })


}