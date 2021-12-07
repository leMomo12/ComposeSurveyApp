package com.mnowo.composesurveyapp.feature_add_survey

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Title
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mnowo.composesurveyapp.R
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.util.TestTags
import com.mnowo.composesurveyapp.feature_add_survey.presentation.new_survey.NewSurveyEvent
import com.mnowo.composesurveyapp.feature_add_survey.presentation.new_survey.NewSurveyViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NewSurveyScreen(
    onNavigate: (String) -> Unit = {},
    viewModel: NewSurveyViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val titleState = viewModel.titleState.value
    val descriptionState = viewModel.descriptionState.value

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
                    onNavigate(event.route)
                }
                is UiEvent.ShowSnackbar -> {

                }
            }

        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        IconButton(onClick = {
            viewModel.onEvent(NewSurveyEvent.BackPress)
        }, modifier = Modifier.testTag(TestTags.NEW_SURVEY_TO_HOME_BUTTON)) {
            Icon(
                Icons.Default.ArrowBackIos,
                contentDescription = ""
            )
        }
        Text(
            text = "Get started,",
            fontSize = 32.sp,
            fontFamily = istokweb,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 25.dp, start = 20.dp, end = 20.dp)
        )
        Text(
            text = "and create a new Survey",
            fontSize = 32.sp,
            fontFamily = istokweb,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        )
        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        OutlinedTextField(
            value = titleState.text,
            onValueChange = {
                viewModel.onEvent(NewSurveyEvent.EnteredTitle(it))
            },
            leadingIcon = {
                Icon(Icons.Default.Title, "")
            },
            isError = viewModel.isTitleError.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .testTag(TestTags.NEW_SURVEY_TITLE),
            label = {
                Text(text = stringResource(R.string.enterSurveyTitle))

            },
            singleLine = true
        )
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        OutlinedTextField(
            value = descriptionState.text,
            onValueChange = {
                viewModel.onEvent(NewSurveyEvent.EnteredDescription(it))
            },
            leadingIcon = {
                Icon(Icons.Default.Description, "")
            },
            isError = viewModel.isDescriptionError.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .testTag(TestTags.NEW_SURVEY_DESCRIPTION),
            label = {
                Text(text = stringResource(R.string.enterSurveyDescription))
            },
            singleLine = true
        )
        Spacer(modifier = Modifier.padding(vertical = 40.dp))
        Button(
            onClick = {
                viewModel.onEvent(NewSurveyEvent.AddTitleAndDescription)
            },
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .testTag(TestTags.NEW_SURVEY_BUTTON)
        ) {
            Text(text = "Next Step", fontFamily = istokweb, fontSize = 25.sp)
        }

    }
}
