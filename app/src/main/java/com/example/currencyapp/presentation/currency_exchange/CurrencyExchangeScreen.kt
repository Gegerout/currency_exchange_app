package com.example.currencyapp.presentation.currency_exchange

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CurrencyExchangeScreen(viewModel: CurrencyExchangeViewModel = viewModel()) {
    var baseCurrency by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = baseCurrency,
            onValueChange = { baseCurrency = it },
            label = { Text("Base Currency") },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Button(
            onClick = {
                viewModel.getCurrencies(baseCurrency)
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(text = "Get Currencies")
        }

        val currentState by remember(viewModel.state) { viewModel.state }

        if (currentState.isLoading) {
            Text(text = "Loading...")
        } else if (currentState.error.isNotBlank()) {
            Text(text = "Error: ${currentState.error}")
        } else {
            currentState.currencies.let { currencies ->
                Text(
                    text = "Currencies: EUR=${currencies.EUR}, USD=${currencies.USD}, CAD=${currencies.CAD}, GBP=${currencies.GBP}",
                )
            }
        }
    }
}
