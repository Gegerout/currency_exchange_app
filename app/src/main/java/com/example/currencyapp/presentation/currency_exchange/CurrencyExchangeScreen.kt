package com.example.currencyapp.presentation.currency_exchange

import CurrencySelector
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun CurrencyExchangeScreen(
    navController: NavController,
    viewModel: CurrencyExchangeViewModel = hiltViewModel()
) {
    var amount by remember { mutableStateOf("") }
    var sourceCurrency by remember { mutableStateOf("USD") }
    var targetCurrency by remember { mutableStateOf("EUR") }

    val currencies = listOf("USD", "EUR", "GBP", "CAD")

    Column(modifier = Modifier.padding(16.dp)) {
        // Amount input field
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Source currency selector
        CurrencySelector(
            currencies = currencies,
            selectedCurrency = sourceCurrency,
            onCurrencySelected = { selected ->
                sourceCurrency = selected
                // Reset target currency if it matches the newly selected source currency
                if (targetCurrency == selected) {
                    targetCurrency = currencies.first { it != selected }
                }
            }
        )

        // Filtered target currencies
        val filteredTargetCurrencies = currencies.filter { it != sourceCurrency }

        // Target currency selector
        CurrencySelector(
            currencies = filteredTargetCurrencies,
            selectedCurrency = targetCurrency,
            onCurrencySelected = { selected ->
                targetCurrency = selected
                // Reset source currency if it matches the newly selected target currency
                if (sourceCurrency == selected) {
                    sourceCurrency = currencies.first { it != selected }
                }
            }
        )

        val currentState by remember { viewModel.state }

        // Convert button
        Button(
            onClick = {
                viewModel.convertCurrency(
                    amount.toDoubleOrNull() ?: 0.0,
                    sourceCurrency,
                    targetCurrency
                )
                navController.navigate("convertedResult")
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(text = "Convert")
        }

        if (currentState.isLoading) {
            Text(text = "Loading...")
        } else if (currentState.error.isNotBlank()) {
            Text(text = "Error: ${currentState.error}")
        }
    }
}
