package com.example.currencyapp.data.remote.dto

import com.example.currencyapp.domain.model.Currency

data class CurrencyDto(
    val CAD: Double,
    val EUR: Double,
    val GBP: Double,
    val USD: Double
)

fun CurrencyDto.toCurrency(): Currency {
    return Currency(
        CAD = CAD,
        EUR = EUR,
        GBP = GBP,
        USD = USD
    )
}