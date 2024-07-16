package com.example.currencyapp.presentation.currency_exchange

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyapp.common.Constants.API_KEY
import com.example.currencyapp.common.Constants.BASE_ERROR_MESSAGE
import com.example.currencyapp.common.Constants.CURRENCIES
import com.example.currencyapp.common.Resource
import com.example.currencyapp.domain.model.Currency
import com.example.currencyapp.domain.usecase.GetCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CurrencyExchangeViewModel @Inject constructor(private val getCoinsUseCase: GetCurrenciesUseCase) :
    ViewModel() {
    private val _state = mutableStateOf<CurrencyExchangeState>(CurrencyExchangeState())
    val state: State<CurrencyExchangeState> = _state

    private fun getCurrencies(baseCurrency: String) {
        getCoinsUseCase(API_KEY, CURRENCIES, baseCurrency).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CurrencyExchangeState(currencies = result.data ?: Currency(0.0,0.0,0.0,0.0))
                }

                is Resource.Error -> {
                    _state.value =
                        CurrencyExchangeState(error = result.message ?: BASE_ERROR_MESSAGE)
                }

                is Resource.Loading -> {
                    _state.value = CurrencyExchangeState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}