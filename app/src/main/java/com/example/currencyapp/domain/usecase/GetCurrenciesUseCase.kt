package com.example.currencyapp.domain.usecase

import com.example.currencyapp.common.Constants.BASE_ERROR_MESSAGE
import com.example.currencyapp.common.Resource
import com.example.currencyapp.data.remote.dto.toCurrency
import com.example.currencyapp.domain.model.Currency
import com.example.currencyapp.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(private val repository: CurrencyRepository) {
    operator fun invoke(apiKey: String, baseCurrency: String, currencies: String): Flow<Resource<Currency>> = flow {
        try {
            emit(Resource.Loading<Currency>())
            val currency = repository.getCurrencies(apiKey, currencies, baseCurrency).toCurrency()
            emit(Resource.Success<Currency>(currency))
        } catch (e: HttpException) {
            emit(Resource.Error<Currency>(e.localizedMessage ?: BASE_ERROR_MESSAGE))
        } catch (e: IOException) {
            emit(Resource.Error<Currency>("Couldn't reach server. Check your internet connection"))
        }
    }
}