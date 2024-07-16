package com.example.currencyapp.presentation.currency_exchange

import com.example.currencyapp.domain.model.Currency

data class CurrencyExchangeState (
    val isLoading: Boolean = false,
    val currencies: Currency = Currency(0.0,0.0,0.0,0.0),
    val error: String = ""
)