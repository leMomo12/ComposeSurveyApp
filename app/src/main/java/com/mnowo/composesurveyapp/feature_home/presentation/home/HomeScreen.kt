package com.mnowo.composesurveyapp.feature_home.presentation.home

import android.os.Bundle
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mnowo.composesurveyapp.R
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.ui.theme.blue
import com.mnowo.composesurveyapp.core.ui.theme.grey
import com.mnowo.composesurveyapp.core.ui.theme.white
import com.mnowo.composesurveyapp.core.util.Constants
import com.mnowo.composesurveyapp.core.util.TestTags
import com.mnowo.composesurveyapp.feature_home.domain.models.SurveyInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {},
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()

    val surveyInfoList = viewModel.surveyInfoList.value.toMutableList()

    val animationDuration = 400

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
                    navController.currentBackStackEntry?.arguments =
                        Bundle().apply {
                            putParcelable(Constants.PARAM_SURVEY_INFO, viewModel.surveyInfoState.value)
                        }
                    onNavigate(event.route)
                }
                is UiEvent.ShowSnackbar -> {

                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {

            StandardHomeTopBar(
                lazyListState = lazyListState,
                animationDuration = animationDuration,
                viewModel = viewModel
            )
            SecondHomeTopBar(lazyListState = lazyListState, animationDuration = animationDuration)
            ListHomeTopBar(
                lazyListState = lazyListState,
                animationDuration = animationDuration,
                coroutineScope = coroutineScope
            )
        }
    ) {


        LazyColumn(
            state = lazyListState
        ) {
            item {

            }
            item {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                ) {
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Text(
                        text = "Today's Surveys",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = istokweb
                    )
                    Text(
                        text = "5 upcoming surveys",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.SansSerif,
                        color = grey
                    )
                    Spacer(modifier = Modifier.padding(vertical = 20.dp))
                    CategoryRow()
                    Spacer(modifier = Modifier.padding(vertical = 5.dp))
                    CategoryRow()
                    Spacer(modifier = Modifier.padding(vertical = 5.dp))
                    CategoryRow()
                    Spacer(modifier = Modifier.padding(vertical = 15.dp))
                    Text(
                        text = "All Surveys",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = istokweb
                    )
                    Spacer(modifier = Modifier.padding(vertical = 2.dp))
                }
            }
            items(surveyInfoList) {
                SurveyListItem(data = it, viewModel)
            }
        }
    }
}


@ExperimentalAnimationApi
@Composable
fun SurveyListItem(data: SurveyInfo, viewModel: HomeViewModel) {

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
            if(expandedState) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = {
                        viewModel.onEvent(HomeEvent.SurveyInfoState(data = data))
                        viewModel.onEvent(HomeEvent.NavigateToBeforeSurvey)
                    }) {
                        Text(text = "Start Survey")
                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    Icon(Icons.Default.LockClock, contentDescription = "", tint = grey, modifier = Modifier.scale(0.7f))
                    Text(text = "5-10 min", fontSize = 16.sp)
                }
            }
        }
    }
}


@ExperimentalAnimationApi
@Composable
fun StandardHomeTopBar(
    lazyListState: LazyListState,
    animationDuration: Int,
    viewModel: HomeViewModel
) {
    AnimatedVisibility(
        visible = lazyListState.firstVisibleItemIndex == 0,
        exit = fadeOut(animationSpec = tween(animationDuration)),
        enter = fadeIn(animationSpec = tween(animationDuration))
    ) {
        Row(
            Modifier
                .padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.ShortText, contentDescription = "", Modifier.scale(1.3f))
            Icon(Icons.Outlined.Add, contentDescription = "",
                Modifier
                    .scale(1.3f)
                    .clickable {
                        viewModel.onEvent(HomeEvent.AddNewSurvey)
                    }
                    .testTag(TestTags.HOME_TO_NEW_SURVEY_BUTTON)
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun SecondHomeTopBar(
    lazyListState: LazyListState,
    animationDuration: Int,
) {
    val istokweb = FontFamily(
        Font(R.font.istokweb_bold, FontWeight.Bold),
        Font(R.font.istokweb_bolditalic, FontWeight.ExtraBold),
        Font(R.font.istokweb_italic, FontWeight.Normal),
        Font(R.font.istokweb_regular, FontWeight.Medium),
    )
    AnimatedVisibility(
        visible = lazyListState.firstVisibleItemIndex == 1,
        exit = fadeOut(animationSpec = tween(animationDuration)),
        enter = fadeIn(animationSpec = tween(animationDuration))
    ) {
        Row(
            Modifier
                .padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Todays surveys",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = istokweb
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun ListHomeTopBar(
    lazyListState: LazyListState,
    animationDuration: Int,
    coroutineScope: CoroutineScope
) {
    val istokweb = FontFamily(
        Font(R.font.istokweb_bold, FontWeight.Bold),
        Font(R.font.istokweb_bolditalic, FontWeight.ExtraBold),
        Font(R.font.istokweb_italic, FontWeight.Normal),
        Font(R.font.istokweb_regular, FontWeight.Medium),
    )
    AnimatedVisibility(
        visible = lazyListState.firstVisibleItemIndex > 1,
        exit = fadeOut(animationSpec = tween(animationDuration)),
        enter = fadeIn(animationSpec = tween(animationDuration))
    ) {
        Row(
            Modifier
                .padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "All Surveys",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = istokweb
            )
            Icon(Icons.Default.ArrowDropUp, contentDescription = "", modifier = Modifier
                .scale(1.3f)
                .clickable {
                    coroutineScope.launch {
                        lazyListState.animateScrollToItem(0)
                    }
                }
            )
        }
    }
}

@Composable
fun CategoryRow(
) {
    Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
        Card(
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, Color.LightGray),
            modifier = Modifier
                .height(60.dp)
                .width(160.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
            ) {
                Box(
                    Modifier
                        .height(40.dp)
                        .width(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            color = blue
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Dns, "", tint = white)
                }
                Text(
                    text = "Science",
                    modifier = Modifier.padding(start = 10.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Card(
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, Color.LightGray),
            modifier = Modifier
                .height(60.dp)
                .width(160.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
            ) {
                Box(
                    Modifier
                        .height(40.dp)
                        .width(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            blue
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Dns, "", tint = white)
                }
                Text(
                    text = "Science",
                    modifier = Modifier.padding(start = 10.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}