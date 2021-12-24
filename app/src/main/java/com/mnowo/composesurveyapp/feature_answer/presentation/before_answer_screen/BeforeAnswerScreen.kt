package com.mnowo.composesurveyapp.feature_answer.presentation.before_answer_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mnowo.composesurveyapp.R
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BeforeAnswerScreen(
    onNavigate: (String) -> Unit = {},
    viewModel: BeforeAnswerViewModel = hiltViewModel()
) {

    val istokweb = FontFamily(
        Font(R.font.istokweb_bold, FontWeight.Bold),
        Font(R.font.istokweb_bolditalic, FontWeight.ExtraBold),
        Font(R.font.istokweb_italic, FontWeight.Normal),
        Font(R.font.istokweb_regular, FontWeight.Medium),
    )

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {

                }
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {
        Text(
            text = "Ready to answer a Survey?",
            fontSize = 30.sp,
            fontFamily = istokweb,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Here is some information:",
            fontSize = 30.sp,
            fontFamily = istokweb,
            fontWeight = FontWeight.Bold
        )

    }
}