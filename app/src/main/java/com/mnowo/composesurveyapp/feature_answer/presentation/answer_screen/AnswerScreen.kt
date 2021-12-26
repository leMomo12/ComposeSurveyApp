package com.mnowo.composesurveyapp.feature_answer.presentation.answer_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mnowo.composesurveyapp.R
import com.mnowo.composesurveyapp.core.ui.theme.blue
import com.mnowo.composesurveyapp.core.ui.theme.grey
import com.mnowo.composesurveyapp.core.ui.theme.lightBlue2

@Composable
fun AnswerScreen(onNavigate: (String) -> Unit = {}) {

    val istokweb = FontFamily(
        Font(R.font.istokweb_bold, FontWeight.Bold),
        Font(R.font.istokweb_bolditalic, FontWeight.ExtraBold),
        Font(R.font.istokweb_italic, FontWeight.Normal),
        Font(R.font.istokweb_regular, FontWeight.Medium),
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(Modifier.fillMaxWidth()) {
            Icon(Icons.Default.ArrowBackIos, contentDescription = "")
            Text(
                text = "2 of 10",
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = istokweb,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        LinearProgressIndicator(
            progress = 0.2f,
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
            text = "Which of the following cirles do you like the most?",
            fontSize = 28.sp,
            fontFamily = istokweb,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 70.dp)
        )
        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        LazyColumn {
            items(4) {
                AnswerListItem(istokweb = istokweb)
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
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
fun AnswerListItem(istokweb: FontFamily) {
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, Color.LightGray),
    ) {
        Text(
            text = "Arctic Circle",
            fontSize = 18.sp,
            fontFamily = istokweb,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 25.dp, top = 20.dp, bottom = 20.dp, end = 25.dp)
        )
    }
}