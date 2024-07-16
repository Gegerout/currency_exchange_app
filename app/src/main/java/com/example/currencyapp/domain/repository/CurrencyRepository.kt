package com.example.currencyapp.domain.repository

import com.example.currencyapp.data.remote.dto.CurrencyDto

interface CurrencyRepository {
    suspend fun getCurrencies(apiKey: String, baseCurrency: String, currencies: String): CurrencyDto
}