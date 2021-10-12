package com.mnowo.composesurveyapp.feature_auth.domain.use_case

import android.util.Log
import com.mnowo.composesurveyapp.core.presentation.util.Resource
import com.mnowo.composesurveyapp.core.util.ValidationUtil
import com.mnowo.composesurveyapp.feature_auth.domain.models.RegisterResult
import com.mnowo.composesurveyapp.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(email: String, password: String) : Flow<Resource<RegisterResult>> = flow {
        Log.d("Registration", "In UseCase")
        try {
            emit(Resource.Loading<RegisterResult>())
            val emailResult = ValidationUtil.validateEmail(email)
            val passwordResult = ValidationUtil.validateText(password)

            if(emailResult.isValidInput == true || passwordResult.isValidInput == true) {
                val respondResult = authRepository.registerWithEmailAndPassword(email, password)
                val registerResult = RegisterResult(registerResult = respondResult)
                emit(Resource.Success(registerResult))
            } else {
                emit(Resource.Error<RegisterResult>(message = "Invalid inputs"))
            }

        } catch (e: Exception) {
            Log.d("Registration", "UseCaseError ${e.message}")
            emit(Resource.Error<RegisterResult>(message = e.localizedMessage ?: "An unexpected error occurred" ))
        }
    }
}