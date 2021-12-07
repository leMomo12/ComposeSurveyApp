package com.mnowo.composesurveyapp.feature_add_survey.presentation.done_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mnowo.composesurveyapp.R
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.ui.theme.blue
import com.mnowo.composesurveyapp.core.ui.theme.grey
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DoneScreen(onNavigate: (String) -> Unit, viewModel: DoneViewModel = hiltViewModel()) {

    val istokweb = FontFamily(
        Font(R.font.istokweb_bold, FontWeight.Bold),
        Font(R.font.istokweb_bolditalic, FontWeight.ExtraBold),
        Font(R.font.istokweb_italic, FontWeight.Normal),
        Font(R.font.istokweb_regular, FontWeight.Medium),
    )

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest {
            when(it) {
                is UiEvent.Navigate -> {
                    onNavigate(it.route)
                }
            }
        }
    }

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.padding(vertical = 90.dp))
        Card(
            shape = CircleShape, backgroundColor = blue, modifier = Modifier
                .size(180.dp)
                .padding(30.dp), contentColor = Color.White
        ) {
            Icon(Icons.Default.Check, contentDescription = "", Modifier.padding(10.dp))
        }
        Text(
            text = "Done!",
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = istokweb
        )
        Spacer(modifier = Modifier.padding(vertical = 15.dp))
        Text(text = "You have successfully published your survey", fontSize = 18.sp, fontWeight = FontWeight.Light, color = grey)
        Spacer(modifier = Modifier.padding(vertical = 15.dp))
        Button(
            onClick = {
                      viewModel.onEvent(DoneEvent.doneBtnClicked)
            },
            Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Text(text = "Ok!", fontFamily = istokweb, fontSize = 20.sp)
        }
    }
}
