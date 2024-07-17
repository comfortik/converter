package com.example.converter.data.repositories

import com.example.converter.data.retrofit.ExhangeRateSingleton
import com.example.converter.domain.model.CurrencyResponse

class ExchangeCurrensiesRepository {
    suspend fun getCurrencies(baseCurrency: String, currencies: String, type: String? = null, apiKey: String): CurrencyResponse {
        return ExhangeRateSingleton().newInstance().loadCurrencies(baseCurrency, currencies, type, apiKey)
    }
}
