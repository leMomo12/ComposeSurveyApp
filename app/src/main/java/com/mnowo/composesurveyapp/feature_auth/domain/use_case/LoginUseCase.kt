package com.mnowo.composesurveyapp.feature_auth.domain.use_case

import android.provider.Settings.Global.getString
import android.util.Log
import com.mnowo.composesurveyapp.R
import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.core.util.ValidationUtil
import com.mnowo.composesurveyapp.feature_auth.domain.models.LoginResult
import com.mnowo.composesurveyapp.feature_auth.domain.models.RegisterResult
import com.mnowo.composesurveyapp.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {

    operator fun invoke(email: String, password: String) : Flow<Resource<LoginResult>> = flow {
        try {
            emit(Resource.Loading<LoginResult>())
            val emailResult = ValidationUtil.validateEmail(email)
            val passwordResult = ValidationUtil.validateText(password)

            if(emailResult.isValidInput == true && passwordResult.isValidInput == true) {
                val respondResult = authRepository.signInWithEmailAndPassword(email, password)
                val loginResult = LoginResult(loginResult = respondResult)

                if(respondResult.errorMessage != null) {
                    emit(Resource.Error<LoginResult>(message = respondResult.errorMessage.toString()))
                } else {
                    emit(Resource.Success(loginResult))
                }
            } else {
                if(emailResult.isInvalidInput == true) {
                    emit(Resource.Error<LoginResult>(data = LoginResult(emailError = true), message = "Error"))
                }
                if(passwordResult.isInvalidInput == true) {
                    emit(Resource.Error<LoginResult>(data = LoginResult(passwordError = true), message = "Error"))
                }
                if(emailResult.isInvalidEmail == true) {
                    emit(Resource.Error<LoginResult>(data = LoginResult(emailError = true), message = "Error"))
                }
            }

        } catch (e: Exception) {
            Log.d("Registration", "UseCaseError ${e.message}")
            emit(Resource.Error<LoginResult>(message = e.localizedMessage ?: "An unexpected error occurred" ))
        }
    }
}