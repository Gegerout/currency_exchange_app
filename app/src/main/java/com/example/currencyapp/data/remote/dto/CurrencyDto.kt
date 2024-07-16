package com.example.currencyapp.data.remote.dto

import com.example.currencyapp.domain.model.Currency

data class CurrencyDto(
    val `data`: Data
)

fun CurrencyDto.toCurrency() : Currency {
    return Currency(
        EUR = data.EUR,
        USD = data.USD,
        GBP = data.GBP,
        CAD = data.CAD
    )
}