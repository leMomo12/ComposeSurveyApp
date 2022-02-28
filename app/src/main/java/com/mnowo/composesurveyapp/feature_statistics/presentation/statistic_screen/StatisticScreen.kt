package com.mnowo.composesurveyapp.feature_statistics

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mnowo.composesurveyapp.R
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.ui.theme.grey
import com.mnowo.composesurveyapp.core.ui.theme.lightBlue2
import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion
import com.mnowo.composesurveyapp.feature_statistics.presentation.statistic_screen.StatisticEvent
import com.mnowo.composesurveyapp.feature_statistics.presentation.statistic_screen.StatisticViewModel
import kotlinx.coroutines.flow.collectLatest


@Composable
fun StatisticScreen(onNavigate: (String) -> Unit, viewModel: StatisticViewModel = hiltViewModel(), surveyPath: String) {
    val scaffoldState = rememberScaffoldState()


    val istokweb = FontFamily(
        Font(R.font.istokweb_bold, FontWeight.Bold),
        Font(R.font.istokweb_bolditalic, FontWeight.ExtraBold),
        Font(R.font.istokweb_italic, FontWeight.Normal),
        Font(R.font.istokweb_regular, FontWeight.Medium),
    )

    LaunchedEffect(key1 = true) {
        viewModel.setSurveyPath(surveyPath)
        viewModel.getSurveyAnswers()
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
        if (!viewModel.state.value.isLoading) {
            StatisticScreenContent(
                scaffoldState = scaffoldState,
                istokweb = istokweb,
                viewModel = viewModel
            )
        } else {
            Text(text = "Loading...", fontSize = 30.sp)
        }
    }
}

@Composable
fun StatisticScreenContent(
    scaffoldState: ScaffoldState,
    istokweb: FontFamily,
    viewModel: StatisticViewModel
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Watch your",
            fontSize = 32.sp,
            fontFamily = istokweb,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 25.dp, start = 20.dp, end = 20.dp)
        )
        Text(
            text = "Survey's statistics",
            fontSize = 32.sp,
            fontFamily = istokweb,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        )
        Spacer(modifier = Modifier.padding(vertical = 15.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Icon(Icons.Default.ArrowLeft, contentDescription = "",
                Modifier
                    .scale(1.6f)
                    .clickable {
                        viewModel.onEvent(StatisticEvent.PreviousSurvey)
                    })
            Spacer(modifier = Modifier.padding(horizontal = 20.dp))
            Text(text = "${viewModel.currentQuestion.value.plus(1)} / ${viewModel.surveyListData.value.size}", color = grey, fontFamily = istokweb)
            Spacer(modifier = Modifier.padding(horizontal = 20.dp))
            Icon(Icons.Default.ArrowRight, contentDescription = "",
                Modifier
                    .scale(1.6f)
                    .clickable {
                        viewModel.onEvent(StatisticEvent.NextSurvey)
                    })
        }
        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        Text(
            text = viewModel.surveyListData.value[viewModel.currentQuestion.value].questionTitle,
            fontSize = 28.sp,
            fontFamily = istokweb,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 20.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        LazyColumn {
            items(4) {
                StatisticAnswerItem(
                    istokweb = istokweb,
                    viewModel = viewModel,
                    data = viewModel.surveyListData.value[viewModel.currentQuestion.value],
                    index = it
                )
            }
        }
    }
}

@Composable
fun StatisticAnswerItem(
    istokweb: FontFamily,
    viewModel: StatisticViewModel,
    data: GetQuestion,
    index: Int
) {
    var text by remember {
        mutableStateOf("")
    }

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(10.dp),
            border = BorderStroke(1.dp, color = Color.LightGray),
        ) {
            when (index) {
                0 -> {
                    text = data.questionOne.toString()
                }
                1 -> {
                    text = data.questionTwo.toString()
                }
                2 -> {
                    text = data.questionThree.toString()
                }
                3 -> {
                    text = data.questionFour.toString()
                }
            }

            Text(
                text = text,
                fontSize = 18.sp,
                fontFamily = istokweb,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 25.dp, top = 20.dp, bottom = 20.dp, end = 25.dp)
            )
        }
        Spacer(modifier = Modifier.padding(horizontal = 10.dp))
        Text(text = "100%", fontFamily = istokweb, fontWeight = FontWeight.Normal, color = grey)
    }
}

