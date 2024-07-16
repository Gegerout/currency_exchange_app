package com.example.currencyapp.data.repository

import com.example.currencyapp.data.remote.ExchangeCurrencyApi
import com.example.currencyapp.data.remote.dto.CurrencyDto
import com.example.currencyapp.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(private val api: ExchangeCurrencyApi) : CurrencyRepository {
    override suspend fun getCurrencies(apiKey: String, baseCurrency: String, currencies: String): CurrencyDto {
        return api.getCurrencies(apiKey, currencies, baseCurrency)
    }
}