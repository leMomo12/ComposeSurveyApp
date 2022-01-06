package com.mnowo.composesurveyapp.feature_answer.domain.use_case

import com.mnowo.composesurveyapp.feature_answer.domain.repository.AnswerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class DeleteCachedQuestions @Inject constructor(
    private val repository: AnswerRepository
){

    suspend operator fun invoke() = repository.deleteAllCachedQuestions()
}