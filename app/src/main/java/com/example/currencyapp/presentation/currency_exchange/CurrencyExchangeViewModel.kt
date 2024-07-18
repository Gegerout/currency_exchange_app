package com.example.currencyapp.presentation.currency_exchange

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyapp.common.Constants.API_KEY
import com.example.currencyapp.common.Constants.BASE_CURRENCY
import com.example.currencyapp.common.Constants.BASE_ERROR_MESSAGE
import com.example.currencyapp.common.Constants.CURRENCIES
import com.example.currencyapp.common.Resource
import com.example.currencyapp.domain.model.Currency
import com.example.currencyapp.domain.usecase.GetCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CurrencyExchangeViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(CurrencyExchangeState())
    val state: State<CurrencyExchangeState> = _state

    private val _conversionComplete = MutableStateFlow(false)
    val conversionComplete: StateFlow<Boolean> = _conversionComplete

    init {
        savedStateHandle.get<String>(BASE_CURRENCY)?.let { baseCurrency ->
            getCurrencies(baseCurrency)
        }
    }

    fun convertCurrency(amount: Double, sourceCurrency: String, targetCurrency: String) {
        _conversionComplete.value = false
        getCurrencies(sourceCurrency, amount, targetCurrency)
    }

    private fun getCurrencies(baseCurrency: String, amount: Double = 1.0, targetCurrency: String = "") {
        getCurrenciesUseCase(
            apiKey = API_KEY,
            currencies = CURRENCIES,
            baseCurrency = baseCurrency
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val rates = result.data ?: Currency(0.0, 0.0, 0.0, 0.0)
                    val targetRate = when (targetCurrency) {
                        "USD" -> rates.USD
                        "EUR" -> rates.EUR
                        "GBP" -> rates.GBP
                        "CAD" -> rates.CAD
                        else -> 0.0
                    }
                    val convertedAmount = amount * targetRate
                    _state.value = CurrencyExchangeState(
                        currencies = rates,
                        convertedAmount = convertedAmount
                    )
                    _conversionComplete.value = true
                }

                is Resource.Error -> {
                    _state.value = CurrencyExchangeState(error = result.message ?: BASE_ERROR_MESSAGE)
                }

                is Resource.Loading -> {
                    _state.value = CurrencyExchangeState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun resetConversionComplete() {
        _conversionComplete.value = false
        _state.value = CurrencyExchangeState(convertedAmount = null)
    }
}
