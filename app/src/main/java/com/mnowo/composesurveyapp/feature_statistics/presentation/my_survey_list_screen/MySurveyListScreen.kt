package com.mnowo.composesurveyapp.feature_statistics

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mnowo.composesurveyapp.R
import com.mnowo.composesurveyapp.core.ui.theme.grey
import com.mnowo.composesurveyapp.core.domain.models.SurveyInfo
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.util.asString
import com.mnowo.composesurveyapp.feature_statistics.presentation.my_survey_list_screen.MySurveyListEvent
import com.mnowo.composesurveyapp.feature_statistics.presentation.my_survey_list_screen.MySurveyListViewModel
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.ui.unit.dp as dp

@ExperimentalAnimationApi
@Composable
fun MySurveyListScreen(
    onNavigate: (String) -> Unit,
    viewModel: MySurveyListViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    val lazyListState = rememberLazyListState()

    val istokweb = FontFamily(
        Font(R.font.istokweb_bold, FontWeight.Bold),
        Font(R.font.istokweb_bolditalic, FontWeight.ExtraBold),
        Font(R.font.istokweb_italic, FontWeight.Normal),
        Font(R.font.istokweb_regular, FontWeight.Medium),
    )

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

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest {
            when (it) {
                is UiEvent.Navigate -> {
                    onNavigate(it.route)
                }
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it.uiText.asString(context = context)
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MySurveyStandardTopBar(
                lazyListState = lazyListState,
                animationDuration = 400,
                viewModel
            )

            MySurveyListTopBar(
                lazyListState = lazyListState,
                animationDuration = 400,
                istokweb = istokweb
            )
        }
    ) {
        Column(Modifier.fillMaxSize()) {
            LazyColumn(state = lazyListState) {
                item {
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

                if (!viewModel.state.value.isLoading) {
                    items(viewModel.surveyInfoList.value) {
                        StatisticsListItem(data = it, viewModel = viewModel)
                    }
                } else {
                    items(5) {
                        ShimmerMySurveyListItem(brush = brush)
                    }
                }
            }
        }
    }
}

@Composable
fun StatisticsListItem(data: SurveyInfo, viewModel: MySurveyListViewModel) {
    var expandedState by remember {
        mutableStateOf(false)
    }

    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 400,
                    easing = LinearOutSlowInEasing
                )
            )
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp, top = 10.dp)
            .clickable {
                expandedState = !expandedState
            }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Row(Modifier.fillMaxWidth(), Arrangement.End) {
                Text(
                    text = "${data.questionCount} Questions",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.SansSerif,
                    color = grey,
                    maxLines = 2
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 2.dp))
            Text(
                text = data.title,
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = data.description,
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.SansSerif,
                color = grey
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.ThumbUp, contentDescription = "")
                Text(
                    text = "${data.likes}",
                    fontSize = 13.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 6.dp)
                )
                Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                Icon(
                    Icons.Default.ThumbDown,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 10.dp)
                )
                Text(
                    text = "${data.dislikes}",
                    fontSize = 13.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            if (expandedState) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = {
                        viewModel.onEvent(MySurveyListEvent.NavToStatistics)
                    }) {
                        Text(text = "Open statistics")
                    }

                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "",
                        modifier = Modifier.clickable {

                        })
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun MySurveyStandardTopBar(
    lazyListState: LazyListState,
    animationDuration: Int,
    viewModel: MySurveyListViewModel
) {
    AnimatedVisibility(
        visible = lazyListState.firstVisibleItemIndex == 0,
        exit = fadeOut(animationSpec = tween(animationDuration)),
        enter = fadeIn(animationSpec = tween(animationDuration))
    ) {
        Row(Modifier.padding(top = 5.dp, start = 20.dp, bottom = 9.dp, end = 20.dp)) {
            Icon(
                Icons.Default.ArrowBackIos,
                contentDescription = "",
                modifier = Modifier.clickable {
                    viewModel.onEvent(MySurveyListEvent.NavBackToHome)
                }
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun MySurveyListTopBar(lazyListState: LazyListState, animationDuration: Int, istokweb: FontFamily) {
    AnimatedVisibility(
        visible = lazyListState.firstVisibleItemIndex >= 1,
        exit = fadeOut(animationSpec = tween(animationDuration)),
        enter = fadeIn(animationSpec = tween(animationDuration))
    ) {
        Row(Modifier.padding(top = 5.dp, start = 20.dp, bottom = 9.dp, end = 20.dp)) {
            Text(
                text = "My survey's", fontFamily = istokweb,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
            )
            Divider(color = Color.LightGray, thickness = 0.3.dp, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun ShimmerMySurveyListItem(brush: Brush) {
    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp, top = 10.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Row(Modifier.fillMaxWidth(), Arrangement.End) {
                // Question
                Spacer(
                    modifier = Modifier
                        .height(15.dp)
                        .fillMaxWidth(0.2f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(brush = brush)
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 2.dp))
            // Title
            Spacer(
                modifier = Modifier
                    .height(25.dp)
                    .fillMaxWidth(0.6f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(brush = brush)
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            // Description
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth(0.8f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(brush = brush)
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            // Thumb up/down
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Spacer(
                    modifier = Modifier
                        .height(22.dp)
                        .fillMaxWidth(0.2f)
                        .padding(start = 6.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(brush = brush)
                )
                Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                Spacer(
                    modifier = Modifier
                        .height(22.dp)
                        .fillMaxWidth(0.3f)
                        .padding(start = 6.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(brush = brush)
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
        }
    }
}