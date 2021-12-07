package com.mnowo.composesurveyapp.feature_home.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.ShortText
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {}
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

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
            Row(
                Modifier
                    .padding(top = 15.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
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
    ) {
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