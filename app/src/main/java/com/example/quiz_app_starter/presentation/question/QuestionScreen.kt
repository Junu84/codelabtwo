package com.example.quiz_app_starter.presentation.question

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quiz_app_starter.model.Question
import com.example.quiz_app_starter.model.getDummyQuestions
import com.example.quiz_app_starter.ui.theme.QuizappstarterTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(
    questions: List<Question> = getDummyQuestions(),
    currentQuestionIndex: Int = 0,
    onFinish: (Int) -> Unit = {}
) {
    var currentIndex by remember { mutableIntStateOf(currentQuestionIndex) }
    var score by remember { mutableIntStateOf(0) }

    val currentQuestion = questions.getOrNull(currentIndex) ?: questions.last()

    var selectedAnswer by remember(currentIndex) { mutableStateOf<String?>(null) }
    var timeLeft by remember(currentIndex) { mutableFloatStateOf(1f) }

    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    val onDialogConfirm: () -> Unit = {
        showDialog = false
        if (currentIndex < questions.size - 1) {
            currentIndex++
            // Reset states for the next question
            selectedAnswer = null
            timeLeft = 1f
        } else {
            // This is the last question, trigger the finish navigation
            Log.d("QuizApp", "Quiz Completed! Score: $score")
            onFinish(score)
        }
    }

    LaunchedEffect(currentIndex, showDialog) {
        if (!showDialog) {
            val totalTime = 10000L
            val step = 100L
            var remaining = (timeLeft * totalTime).toLong()
            
            while (remaining > 0 && !showDialog) {
                delay(step)
                remaining -= step
                timeLeft = remaining.toFloat() / totalTime
            }
            
            if (remaining <= 0 && !showDialog) {
                dialogMessage = "No answer selected. Time is out."
                showDialog = true
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(text = "Quiz") },
            text = { Text(text = dialogMessage) },
            confirmButton = {
                TextButton(onClick = onDialogConfirm) {
                    Text(text = if (currentIndex < questions.size - 1) "Next Question" else "Finish")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Question ${currentIndex + 1}/${questions.size}") }
            )
        },
        bottomBar = {
            Box(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = {
                        if (selectedAnswer == currentQuestion.correctAnswer) {
                            score++
                            dialogMessage = "Correct!"
                        } else {
                            dialogMessage = "Wrong! The correct answer is: ${currentQuestion.correctAnswer}"
                        }
                        showDialog = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = selectedAnswer != null
                ) {
                    Text(text = if (currentIndex < questions.size - 1) "Submit" else "Finish")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                progress = { timeLeft },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Text(
                    text = currentQuestion.question,
                    modifier = Modifier.padding(24.dp),
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(currentQuestion.answers) { answer ->
                    AnswerCard(
                        answer = answer,
                        isSelected = selectedAnswer == answer,
                        onSelect = { selectedAnswer = answer }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnswerCard(
    answer: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        onClick = onSelect,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = answer,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            RadioButton(
                selected = isSelected,
                onClick = onSelect
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionScreenPreview() {
    QuizappstarterTheme {
        QuestionScreen()
    }
}
