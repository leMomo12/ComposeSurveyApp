package com.mnowo.composesurveyapp.feature_answer.presentation.before_answer_screen

import android.os.Bundle
import android.util.Log.d
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mnowo.composesurveyapp.R
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.ui.theme.blue
import com.mnowo.composesurveyapp.core.ui.theme.grey
import com.mnowo.composesurveyapp.core.util.Constants
import com.mnowo.composesurveyapp.feature_home.domain.models.SurveyInfo
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BeforeAnswerScreen(
    onNavigate: (String) -> Unit = {},
    viewModel: BeforeAnswerViewModel = hiltViewModel(),
    navController: NavController
) {

    val istokweb = FontFamily(
        Font(R.font.istokweb_bold, FontWeight.Bold),
        Font(R.font.istokweb_bolditalic, FontWeight.ExtraBold),
        Font(R.font.istokweb_italic, FontWeight.Normal),
        Font(R.font.istokweb_regular, FontWeight.Medium),
    )


    LaunchedEffect(key1 = true) {
        viewModel.surveyDetail = navController.previousBackStackEntry?.arguments?.getParcelable<SurveyInfo>(Constants.PARAM_SURVEY_INFO)
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navController.currentBackStackEntry?.arguments =
                        Bundle().apply {
                            putString(Constants.PARAM_SURVEY_PATH, viewModel.surveyDetail?.title)
                        }
                    onNavigate(event.route)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        Text(
            text = "Ready to answer a Survey?",
            fontSize = 30.sp,
            fontFamily = istokweb,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp)
        )
        Spacer(modifier = Modifier.padding(top = 5.dp))
        Text(
            text = "Here you have some information:",
            fontSize = 23.sp,
            fontFamily = istokweb,
            fontWeight = FontWeight.Normal,
            color = grey,
            modifier = Modifier.padding(start = 20.dp, top = 10.dp)
        )
        Spacer(modifier = Modifier.padding(top = 40.dp))
        Card(
            shape = RoundedCornerShape(30.dp),
            border = BorderStroke(1.dp, color = Color.LightGray),
            elevation = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Text(
                    text = "Description:",
                    fontSize = 18.sp,
                    fontFamily = istokweb,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                Text(
                    text = viewModel.surveyDetail!!.description,
                    fontSize = 18.sp,
                    fontFamily = istokweb,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.padding(vertical = 15.dp))
                Text(
                    text = "Categories: Science",
                    fontSize = 18.sp,
                    fontFamily = istokweb,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                Text(
                    text = "Expected time: 5-10 minutes",
                    fontSize = 18.sp,
                    fontFamily = istokweb,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                Text(
                    text = "Questions: " + viewModel.surveyDetail!!.questionCount,
                    fontSize = 18.sp,
                    fontFamily = istokweb,
                    fontWeight = FontWeight.Medium
                )
            }

        }
        Spacer(modifier = Modifier.padding(vertical = 50.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = { viewModel.onEvent(BeforeAnswerEvent.NavigateToAnswer) }) {
                Text(text = "Start the survey", fontSize = 18.sp)
            }
        }
        Spacer(modifier = Modifier.padding(50.dp))
    }
}