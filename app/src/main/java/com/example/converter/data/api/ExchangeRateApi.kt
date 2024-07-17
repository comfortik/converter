package com.example.converter.data.api

import com.example.converter.domain.model.CurrencyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {
    @GET("/v3/latest")
    suspend fun loadCurrencies(
        @Query("base_currency") baseCurrency: String ,
        @Query("currencies") currencies: String ,
        @Query("type") type: String? = null,
        @Query("apikey") apiKey: String
    ): CurrencyResponse
}


