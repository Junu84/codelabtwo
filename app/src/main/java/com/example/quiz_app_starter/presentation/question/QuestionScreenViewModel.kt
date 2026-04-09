package com.example.quiz_app_starter.presentation.question

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz_app_starter.model.Question
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.ThreadLocalRandom.current

class QuestionScreenViewModel (
    questions: List<Question>
) : ViewModel(), DefaultLifecycleObserver {

    // _uiState is private to prevent modification (internal)
    // MutableStateFlow is exposed to the UI layer
    // StateFlow is exposed to the UI layer
    // uiState is read-only to prevent modification (external)

    private val _uiState = MutableStateFlow(
        QuestionScreenState(questions = questions)
    )
    val uiState: StateFlow<QuestionScreenState> = _uiState

    // selecting an answer
    fun selectAnswer(answer: String) {
        _uiState.value = _uiState.value.copy(selectedAnswer = answer)

    }

    // submitting an answer
    fun submitAnswer() {
        val state = _uiState.value
        val currentQuestion = state.currentQuestion ?: return

        val isCorrect = state.selectedAnswer == currentQuestion.correctAnswer
        val message = if (isCorrect) {
            "Correct!"
        } else {
            "Wrong! The correct answer is: ${currentQuestion.correctAnswer}"
        }
        _uiState.value = state.copy(
            points = if (isCorrect) state.points + 1 else state.points,
            dialogMessage = message,
            showDialog = true
        )

    }

    fun onDialogConfirm(onFinish: (Int) -> Unit) {
        val state = _uiState.value
        if (state.currentQuestionIndex < state.questions.size - 1) {
            _uiState.value = state.copy(
                currentQuestionIndex = state.currentQuestionIndex + 1,
                selectedAnswer = null,
                timerProgress = 1f,
                showDialog = false
            )
        } else {
            onFinish(state.points)
        }
    }

        private fun startTimer() {

        viewModelScope.launch {
            while (true) {
                delay(100)

                val isTimerRunning = false
                if (isTimerRunning) {
                    val state = _uiState.value
                    val current = state.timerProgress

                    if (current > 0f) {
                        _uiState.value = state.copy(
                            timerProgress = current - 0.01f
                        )
                    } else {
                        //isTimerRunning = false

                        _uiState.value = state.copy(
                            dialogMessage = "No answer selected. Time is out.",
                            showDialog = true
                        )
                    }
                }

            }
        }
    }
}