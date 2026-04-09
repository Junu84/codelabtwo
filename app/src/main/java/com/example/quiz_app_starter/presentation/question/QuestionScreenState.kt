package com.example.quiz_app_starter.presentation.question

import com.example.quiz_app_starter.model.Question

data class QuestionScreenState  (
    val questions: List<Question> = emptyList(),
            val currentQuestionIndex: Int = 0,
    val selectedAnswer: String? = null,
    val score: Int = 0,
            val timerProgress: Float = 1f,
                    val showDialog: Boolean = false,
                            val dialogMessage: String = "",
    val points: Int = 0
                                ) {
    val currentQuestion: Question?
        get() = questions.getOrNull(currentQuestionIndex)

}


