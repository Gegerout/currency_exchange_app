package com.example.currencyapp.presentation.currency_exchange

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ConvertedResultScreen(navController: NavController, convertedAmount: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
        Text(text = "Converted Amount:", modifier = Modifier.padding(vertical = 8.dp))
        Text(text = convertedAmount, style = MaterialTheme.typography.headlineMedium)
    }
}