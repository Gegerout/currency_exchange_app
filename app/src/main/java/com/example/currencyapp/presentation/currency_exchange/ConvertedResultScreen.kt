package com.example.currencyapp.presentation.currency_exchange

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ConvertedResultScreen(
    viewModel: CurrencyExchangeViewModel = hiltViewModel()
) {
    val currentState by remember { viewModel.state }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Converted Amount:", modifier = Modifier.padding(vertical = 8.dp))
        Text(
            text = currentState.convertedAmount?.toString() ?: "0.0",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}