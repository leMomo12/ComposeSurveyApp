package com.mnowo.composesurveyapp.feature_answer.presentation.answer_screen

import android.util.Log.d
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mnowo.composesurveyapp.R
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.ui.theme.blue
import com.mnowo.composesurveyapp.core.ui.theme.lightBlue2
import com.mnowo.composesurveyapp.core.util.Constants
import com.mnowo.composesurveyapp.core.util.asString
import com.mnowo.composesurveyapp.feature_answer.domain.models.GetQuestion
import com.mnowo.composesurveyapp.feature_home.domain.models.SurveyInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AnswerScreen(
    onNavigate: (String) -> Unit = {},
    viewModel: AnswerViewModel = hiltViewModel(),
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val state = viewModel.state.value
    val context = LocalContext.current


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
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        it.uiText.asString(context = context),
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }

    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )
    val transition = rememberInfiniteTransition()
    val translateAime = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAime.value, y = translateAime.value)
    )

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        if (state.isLoading == false) {
            AnswerScreenItem(istokweb = istokweb, viewModel = viewModel)
        } else {
            AnswerShimmerGrid(istokweb = istokweb, brush = brush)
        }
    }
}



@Composable
fun AnswerScreenItem(istokweb: FontFamily, viewModel: AnswerViewModel) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(Modifier.fillMaxWidth()) {
            Icon(Icons.Default.ArrowBackIos, contentDescription = "")
            Text(
                text = "0 of 12",
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = istokweb,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        LinearProgressIndicator(
            progress = 0.0f,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .clip(
                    RoundedCornerShape(60.dp)
                ),
            color = lightBlue2,
        )
        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        Text(
            text = viewModel.currentQuestionData.value.questionTitle,
            fontSize = 28.sp,
            fontFamily = istokweb,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 70.dp)
        )
        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        LazyColumn {
                items(3) {
                    AnswerListItem(istokweb = istokweb, viewModel = viewModel, getQuestion = viewModel.currentQuestionData.value)
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                }
        }
        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Bottom) {
            Button(onClick = { }) {
                Text(
                    text = "Next",
                    fontSize = 21.sp,
                    modifier = Modifier.fillMaxWidth(0.7f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}








@Composable
fun AnswerListItem(istokweb: FontFamily, viewModel: AnswerViewModel, getQuestion: GetQuestion) {

    var color by remember {
        mutableStateOf(Color.LightGray)
    }

    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                color = viewModel.checkColor(oldColor = color)
            },
        border = BorderStroke(1.dp, color = color),
    ) {
        Text(
            text = getQuestion.questionOne.toString(),
            fontSize = 18.sp,
            fontFamily = istokweb,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 25.dp, top = 20.dp, bottom = 20.dp, end = 25.dp)
        )
    }
}

@Composable
fun AnswerShimmerGrid(istokweb: FontFamily, brush: Brush) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(Modifier.fillMaxWidth()) {
            Icon(Icons.Default.ArrowBackIos, contentDescription = "")
            Text(
                text = "0 of 10",
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = istokweb,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        LinearProgressIndicator(
            progress = 0.0f,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .clip(
                    RoundedCornerShape(60.dp)
                ),
            color = lightBlue2,
        )
        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        ShimmerTextItem(fraction = 0.6f, brush = brush, height = 30.dp)
        Spacer(modifier = Modifier.padding(vertical = 5.dp))
        ShimmerTextItem(fraction = 0.45f, brush = brush, height = 30.dp)
        Spacer(modifier = Modifier.padding(vertical = 5.dp))
        ShimmerTextItem(fraction = 0.5f, brush = brush, height = 30.dp)
        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        LazyColumn {
            items(4) {
                ShimmerAnswerListItem(brush = brush)
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = { }, enabled = false) {
                Text(
                    text = "Next",
                    fontSize = 21.sp,
                    modifier = Modifier.fillMaxWidth(0.7f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Composable
fun ShimmerTextItem(fraction: Float, brush: Brush, height: Dp) {
    Spacer(
        modifier = Modifier
            .height(height)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth(fraction = fraction)
            .background(brush = brush)
    )
}

@Composable
fun ShimmerAnswerListItem(brush: Brush) {
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        border = BorderStroke(1.dp, Color.LightGray),
    ) {
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
                .padding(start = 25.dp, top = 20.dp, bottom = 20.dp, end = 25.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(brush = brush)
        )
    }
}
