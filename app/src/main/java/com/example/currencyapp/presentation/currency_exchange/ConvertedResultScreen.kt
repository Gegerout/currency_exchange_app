package com.example.currencyapp.presentation.currency_exchange

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConvertedResultScreen(convertedAmount: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Converted Amount:", modifier = Modifier.padding(vertical = 8.dp))
        Text(text = convertedAmount, style = MaterialTheme.typography.headlineMedium)
    }
}