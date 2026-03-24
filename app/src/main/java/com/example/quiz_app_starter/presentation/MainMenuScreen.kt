package com.example.quiz_app_starter.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.quiz_app_starter.R

@Composable
fun MainMenuScreen(onPlayClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.quiz_logo),
            contentDescription = "Quiz Logo",
            modifier = Modifier.size(200.dp)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = buildAnnotatedString {
                append("Test your ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)) {
                    append("knowledge")
                }
            },
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Button(
            onClick = onPlayClick,
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text(text = "Play!")
        }
    }
}
