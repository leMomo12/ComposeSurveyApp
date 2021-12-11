package com.mnowo.composesurveyapp.feature_home.presentation.home

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
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
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.ui.theme.blue
import com.mnowo.composesurveyapp.core.ui.theme.grey
import com.mnowo.composesurveyapp.core.ui.theme.white
import com.mnowo.composesurveyapp.core.util.TestTags
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {}
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    var scrollOffset = lazyListState.firstVisibleItemIndex

    var visible by remember { mutableStateOf(false) }
    val animationDuration = 700

    val istokweb = FontFamily(
        Font(com.mnowo.composesurveyapp.R.font.istokweb_bold, FontWeight.Bold),
        Font(com.mnowo.composesurveyapp.R.font.istokweb_bolditalic, FontWeight.ExtraBold),
        Font(com.mnowo.composesurveyapp.R.font.istokweb_italic, FontWeight.Normal),
        Font(com.mnowo.composesurveyapp.R.font.istokweb_regular, FontWeight.Medium),
    )

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
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
            AnimatedVisibility(
                visible = lazyListState.firstVisibleItemIndex == 1,
                enter = fadeIn(animationSpec = tween(animationDuration)),
                exit = fadeOut(animationSpec = tween(animationDuration))
            ) {
                Row(
                    Modifier
                        .padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Todays surveys",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = istokweb)
                }
            }
            AnimatedVisibility(
                visible = lazyListState.firstVisibleItemIndex > 1,
                enter = fadeIn(animationSpec = tween(animationDuration)),
                exit = fadeOut(animationSpec = tween(animationDuration))
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
                    Icon(Icons.Default.ArrowDropUp, contentDescription = "", modifier = Modifier.scale(1.3f).clickable {
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(0)
                        }
                    })
                }
            }
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
            items(20) {
                SurveyListItem()
            }
        }
    }
}


@Composable
fun SurveyListItem() {
    Card(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 10.dp, top = 10.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Row(Modifier.fillMaxWidth(), Arrangement.End) {
                Text(
                    text = "5 Questions",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.SansSerif,
                    color = grey
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 2.dp))
            Text(
                text = "Hot or cold?",
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = "Which season do you prefer? Please tell me I will use it for my knowledge",
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.SansSerif,
                color = grey
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.ThumbUp, contentDescription = "")
                Text(
                    text = "16",
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
                    text = "3",
                    fontSize = 13.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
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