package com.example.currencyapp.data.remote

import com.example.currencyapp.data.remote.dto.CurrencyDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeCurrencyApi {
    @GET("/v1/latest")
    suspend fun getCurrencies(
        @Query("apikey") apiKey: String,
        @Query("currencies") currencies: String,
        @Query("base_currency") baseCurrency: String
    ): CurrencyDto
}