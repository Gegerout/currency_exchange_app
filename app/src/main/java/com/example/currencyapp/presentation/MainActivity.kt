package com.example.currencyapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.currencyapp.presentation.currency_exchange.ConvertedResultScreen
import com.example.currencyapp.presentation.currency_exchange.CurrencyExchangeScreen
import com.example.currencyapp.presentation.ui.theme.CurrencyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterApp()
        }
    }
}

@Composable
fun CurrencyConverterApp() {
    CurrencyAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "currencyExchange") {
                composable("currencyExchange") {
                    CurrencyExchangeScreen(navController)
                }
                composable("convertedResult") {
                    ConvertedResultScreen()
                }
            }
        }
    }
}