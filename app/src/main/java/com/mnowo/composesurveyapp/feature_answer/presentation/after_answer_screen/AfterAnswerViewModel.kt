package com.mnowo.composesurveyapp.feature_answer.presentation.after_answer_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mnowo.composesurveyapp.feature_answer.domain.models.Answer
import com.mnowo.composesurveyapp.feature_answer.domain.repository.AnswerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AfterAnswerViewModel @Inject constructor(
    private val answerRepository: AnswerRepository
) : ViewModel() {


    fun addUserSurvey() {
        //val answer = Answer(0, "How are you", "Mood?", answer = Answer.AnswerOptions.SECOND)
        viewModelScope.launch {
          //  answerRepository.addUserAnswer(answer = answer)
        }
    }

}