package com.mnowo.composesurveyapp.feature_statistics

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.mnowo.composesurveyapp.feature_statistics.presentation.statistic_screen.StatisticViewModel
import kotlinx.coroutines.flow.collectLatest


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
                Icon(Icons.Default.ArrowLeft, contentDescription = "", Modifier.scale(1.6f))
                Spacer(modifier = Modifier.padding(horizontal = 20.dp))
                Text(text = "0 / 10", color = grey, fontFamily = istokweb)
                Spacer(modifier = Modifier.padding(horizontal = 20.dp))
                Icon(Icons.Default.ArrowRight, contentDescription = "", Modifier.scale(1.6f))
            }
            Spacer(modifier = Modifier.padding(vertical = 20.dp))
            Text(
                text = "How tall are you?",
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
                        text = "This is the question",
                        percentage = 0.4f,
                        istokweb = istokweb
                    )
                }
            }
        }
    }
}

@Composable
fun StatisticAnswerItem(text: String, percentage: Float, istokweb: FontFamily) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(10.dp),
            border = BorderStroke(1.dp, color = Color.Black),
        ) {
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

