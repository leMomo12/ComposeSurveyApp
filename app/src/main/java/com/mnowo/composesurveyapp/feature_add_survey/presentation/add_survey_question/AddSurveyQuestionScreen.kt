package com.mnowo.composesurveyapp.feature_add_survey.presentation.add_survey_question

import android.util.Log
import android.util.Log.d
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Title
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mnowo.composesurveyapp.R
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.util.TestTags
import com.mnowo.composesurveyapp.core.util.asString
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddSurveyQuestionScreen(
    viewModel: AddSurveyQuestionViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {}
) {
    val state = viewModel.state.value
    val questionTitleState = viewModel.questionTitleState.value
    val questionOneState = viewModel.questionOneState.value
    val questionTwoState = viewModel.questionTwoState.value
    val questionThreeState = viewModel.questionThreeState.value
    val questionFourState = viewModel.questionFourState.value
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val istokweb = FontFamily(
        Font(R.font.istokweb_bold, FontWeight.Bold),
        Font(R.font.istokweb_bolditalic, FontWeight.ExtraBold),
        Font(R.font.istokweb_italic, FontWeight.Normal),
        Font(R.font.istokweb_regular, FontWeight.Medium),
    )

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(event.route)
                }
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {

            Spacer(modifier = Modifier.padding(vertical = 20.dp))
            Text(
                text = stringResource(R.string.addNewQuestion),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = istokweb
            )
            Spacer(modifier = Modifier.padding(vertical = 15.dp))
            OutlinedTextField(
                value = questionTitleState.text,
                onValueChange = {
                    viewModel.onEvent(AddSurveyQuestionEvent.EnteredQuestionTitle(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
                    .testTag(TestTags.ADD_SURVEY_QUESTION_TITLE),
                label = {
                    Text(
                        text = stringResource(R.string.enterQuestionTitle),
                        fontWeight = FontWeight.Light
                    )
                },
                leadingIcon = {
                    Icon(Icons.Outlined.Title, contentDescription = "")
                },
                singleLine = true,
                isError = viewModel.titleError.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                enabled = viewModel.uiEnabled.value
            )

            Spacer(modifier = Modifier.padding(vertical = 20.dp))

            Card(
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(2.dp, Color.LightGray),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 20.dp, end = 20.dp)
                    .background(Color.White)
            ) {
                TextField(
                    value = questionOneState.text,
                    onValueChange = {
                        viewModel.onEvent(AddSurveyQuestionEvent.EnteredQuestionOne(it))
                    },
                    label = {
                        Text(text = stringResource(R.string.enterFirstAnswerOption))
                    },
                    leadingIcon = {
                        Text(text = "1")
                    },
                    modifier = Modifier.testTag(TestTags.ADD_SURVEY_QUESTION_ONE),
                    singleLine = true,
                    enabled = viewModel.uiEnabled.value
                )
            }
            Card(
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(2.dp, Color.LightGray),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 20.dp, end = 20.dp)
                    .background(Color.White)
            ) {
                TextField(
                    value = questionTwoState.text,
                    onValueChange = {
                        viewModel.onEvent(AddSurveyQuestionEvent.EnteredQuestionTwo(it))
                    },
                    label = {
                        Text(text = stringResource(R.string.enterSecondAnswerOption))
                    },
                    leadingIcon = {
                        Text(text = "2")
                    },
                    modifier = Modifier.testTag(TestTags.ADD_SURVEY_QUESTION_TWO),
                    singleLine = true,
                    enabled = viewModel.uiEnabled.value
                )
            }
            Card(
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(2.dp, Color.LightGray),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 20.dp, end = 20.dp)
                    .background(Color.White)
            ) {
                TextField(
                    value = questionThreeState.text,
                    onValueChange = {
                        viewModel.onEvent(AddSurveyQuestionEvent.EnteredQuestionThree(it))
                    },
                    label = {
                        Text(text = stringResource(R.string.enterThirdAnswerOption))
                    },
                    leadingIcon = {
                        Text(text = "3")
                    },
                    modifier = Modifier.testTag(TestTags.ADD_SURVEY_QUESTION_THREE),
                    singleLine = true,
                    enabled = viewModel.uiEnabled.value
                )
            }
            Card(
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(2.dp, Color.LightGray),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 20.dp, end = 20.dp)
                    .background(Color.White)
            ) {
                TextField(
                    value = questionFourState.text,
                    onValueChange = {
                        viewModel.onEvent(AddSurveyQuestionEvent.EnteredQuestionFour(it))
                    },
                    label = {
                        Text(text = stringResource(R.string.enterFouthAnswerOption))
                    },
                    leadingIcon = {
                        Text(text = "4")
                    },
                    modifier = Modifier.testTag(TestTags.ADD_SURVEY_QUESTION_FOUR),
                    singleLine = true,
                    enabled = viewModel.uiEnabled.value
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 20.dp))


            OutlinedButton(
                onClick = {
                    viewModel.onEvent(AddSurveyQuestionEvent.AddQuestion)
                    d("AddQuestion", "Clicked")
                },
                modifier = Modifier.testTag(TestTags.ADD_SURVEY_QUESTION_ADD_QUESTION_BUTTON),
            ) {
                Text(text = "Add Question")
                Icon(Icons.Default.ArrowForward, contentDescription = "IconForward")
            }

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            Button(
                onClick = {
                    viewModel.onEvent(AddSurveyQuestionEvent.PublishSurvey)
                },
                modifier = Modifier.testTag(TestTags.ADD_SURVEY_QUESTION_ADD_SURVEY_BUTTON),
            ) {
                Text(text = "Publish survey")
            }


        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp), contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            Log.d("Login", "isLoading")
            viewModel.setUiEnabled(false)
            CircularProgressIndicator(modifier = Modifier.testTag(TestTags.ADD_SURVEY_QUESTION_PROGRESS_BAR))
        } else {
            viewModel.setUiEnabled(true)
        }
    }
}