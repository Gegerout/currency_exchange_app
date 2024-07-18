package com.example.currencyapp.presentation.currency_exchange

import CurrencySelector
import android.annotation.SuppressLint
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

@SuppressLint("CoroutineCreationDuringComposition")
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
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        CurrencySelector(
            currencies = currencies,
            selectedCurrency = sourceCurrency,
            onCurrencySelected = { selected ->
                sourceCurrency = selected
                if (targetCurrency == selected) {
                    targetCurrency = currencies.first { it != selected }
                }
            }
        )

        val filteredTargetCurrencies = currencies.filter { it != sourceCurrency }

        CurrencySelector(
            currencies = filteredTargetCurrencies,
            selectedCurrency = targetCurrency,
            onCurrencySelected = { selected ->
                targetCurrency = selected
                if (sourceCurrency == selected) {
                    sourceCurrency = currencies.first { it != selected }
                }
            }
        )

        Button(
            onClick = {
                viewModel.convertCurrency(
                    amount.toDoubleOrNull() ?: 0.0,
                    sourceCurrency,
                    targetCurrency
                )
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(text = "Convert")
        }

        val currentState by remember { viewModel.state }

        if (currentState.isLoading) {
            Text(text = "Loading...")
        } else if (currentState.error.isNotBlank()) {
            Text(text = "Error: ${currentState.error}")
        }

        val conversionComplete by viewModel.conversionComplete.collectAsState()

        LaunchedEffect(conversionComplete) {
            if(conversionComplete) {
                navController.navigate("convertedResult/${viewModel.state.value.convertedAmount}") {
                    popUpTo("currencyExchange") { inclusive = false }
                }
                viewModel.resetConversionComplete()
            }
        }
    }
}
