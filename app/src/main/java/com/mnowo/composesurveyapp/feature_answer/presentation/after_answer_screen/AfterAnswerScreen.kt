package com.mnowo.composesurveyapp.feature_answer.presentation.after_answer_screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mnowo.composesurveyapp.R
import com.mnowo.composesurveyapp.core.domain.TextFieldState
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.ui.theme.blue
import com.mnowo.composesurveyapp.core.ui.theme.grey
import com.mnowo.composesurveyapp.core.util.Screen
import com.mnowo.composesurveyapp.core.util.asString
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AfterAnswerScreen(
    onNavigate: (String) -> Unit = {},
    viewModel: AfterAnswerViewModel = hiltViewModel(),
    surveyPath: String
) {

    val istokweb = FontFamily(
        Font(R.font.istokweb_bold, FontWeight.Bold),
        Font(R.font.istokweb_bolditalic, FontWeight.ExtraBold),
        Font(R.font.istokweb_italic, FontWeight.Normal),
        Font(R.font.istokweb_regular, FontWeight.Medium),
    )

    LaunchedEffect(key1 = true) {
        viewModel.setTitle(TextFieldState(text = surveyPath))

        viewModel.eventFlow.collectLatest {
            when(it) {
                is UiEvent.Navigate -> {
                    onNavigate(it.route)
                }
                else -> {}
            }
        }
    }

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.padding(vertical = 70.dp))
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
        Text(
            text = "You have successfully answered the survey",
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            color = grey
        )
        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        Button(
            onClick = {
                viewModel.navigateToHome()
            },
            Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Text(text = "Ok!", fontFamily = istokweb, fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.padding(vertical = 25.dp))
        Text(
            text = "Give the survey a quick response", fontSize = 17.sp,
            fontWeight = FontWeight.Light,
            color = grey
        )
        Spacer(modifier = Modifier.padding(vertical = 5.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Icon(painter = painterResource(
                if (viewModel.thumbUp.value) {
                    R.drawable.ic_thumb_up_filled
                } else {
                    R.drawable.ic_thumb_up_outlined
                }
            ), contentDescription = "", modifier = Modifier.clickable {
                if (viewModel.thumbDown.value) {
                    viewModel.setThumbDown(false)
                    viewModel.setThumbUp(!viewModel.thumbDown.value)
                } else {
                    viewModel.setThumbUp(!viewModel.thumbDown.value)
                }
            })
            Icon(painter = painterResource(
                if (viewModel.thumbDown.value) {
                    R.drawable.ic_thumb_down_filled
                } else {
                    R.drawable.ic_thumb_down_outlined
                }
            ), contentDescription = "", modifier = Modifier.clickable {
                if (viewModel.thumbUp.value) {
                    viewModel.setThumbUp(false)
                    viewModel.setThumbDown(!viewModel.thumbDown.value)
                } else {
                    viewModel.setThumbDown(!viewModel.thumbDown.value)
                }
            })
        }
    }
}