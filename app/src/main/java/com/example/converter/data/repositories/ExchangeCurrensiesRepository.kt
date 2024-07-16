package com.example.converter.data.repositories

import com.example.converter.data.retrofit.ExhangeRateSingleton
import com.example.converter.domain.model.CurrencyResponse
import com.example.converter.features.main.ui.API_KEY
import retrofit2.Call

class ExchangeCurrensiesRepository {
    fun getCurrencies(baseCurrency: String,currencies: String, type: String? = null, apiKey: String) : Call<CurrencyResponse> {
        return ExhangeRateSingleton().newInstance().loadCurrencies(baseCurrency, currencies, type, apiKey)
    }
}