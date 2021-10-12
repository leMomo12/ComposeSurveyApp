package com.mnowo.composesurveyapp.feature_auth.presentation.register

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mnowo.composesurveyapp.core.presentation.util.UiEvent
import com.mnowo.composesurveyapp.core.util.Screen
import com.mnowo.composesurveyapp.core.util.TestTags
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {}
) {
    val state = viewModel.state.value
    val emailState = viewModel.emailState.value
    val passwordState = viewModel.passwordState.value
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

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
        scaffoldState = scaffoldState
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Spacer(modifier = Modifier.padding(vertical = 30.dp))
            Text(
                text = "Don't have an account?",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = istokweb
            )
            Text(
                text = "Create a new",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = istokweb,
                modifier = Modifier.testTag(TestTags.REGISTER_TEXT)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = "Please register for continue",
                fontWeight = FontWeight.Light,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            OutlinedTextField(
                value = emailState.text,
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.EnteredEmail(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                label = {
                    Text(text = "Enter E-Mail", fontWeight = FontWeight.Light)
                },
                leadingIcon = {
                    Icon(Icons.Outlined.Email, contentDescription = "")
                },
                isError = viewModel.isErrorEmail.value,
                enabled = viewModel.uiEnable.value,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)

            )
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            OutlinedTextField(
                value = passwordState.text,
                visualTransformation = if (viewModel.passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.EnteredPassword(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                label = {
                    Text(text = "Enter password", fontWeight = FontWeight.Light)
                },
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, contentDescription = "")
                },
                singleLine = true,
                isError = viewModel.isErrorPassword.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                enabled = viewModel.uiEnable.value,
                trailingIcon = {
                    val image = if (viewModel.passwordVisibility.value)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    IconButton(onClick = {
                        viewModel.setPasswordVisibility(!viewModel.passwordVisibility.value)
                    }) {
                        Icon(imageVector = image, "")
                    }
                }
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Button(
                onClick = {
                    viewModel.onEvent(RegisterEvent.Register)
                },
                Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                enabled = viewModel.uiEnable.value
            ) {
                Text(text = "Register", fontFamily = istokweb, fontSize = 25.sp)
            }
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Text(text = "Already have an account?")
            TextButton(
                onClick = { onNavigate(Screen.LoginScreen.route) },
                enabled = viewModel.uiEnable.value
            ) {
                Text(text = "Login here")
            }


        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp), contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                Log.d("Login", "isLoading")
                viewModel.setUiEnable(false)
                CircularProgressIndicator()
            } else {
                viewModel.setUiEnable(true)
            }
        }
    }
}