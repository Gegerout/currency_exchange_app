package com.example.currencyapp.di

import com.example.currencyapp.common.Constants.BASE_URL
import com.example.currencyapp.data.remote.ExchangeCurrencyApi
import com.example.currencyapp.data.repository.CurrencyRepositoryImpl
import com.example.currencyapp.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePaprikaApi(): ExchangeCurrencyApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ExchangeCurrencyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: ExchangeCurrencyApi) : CurrencyRepository {
        return CurrencyRepositoryImpl(api)
    }
}