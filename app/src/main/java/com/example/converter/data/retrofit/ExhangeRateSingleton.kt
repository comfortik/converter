package com.example.converter.data.retrofit

import com.example.converter.data.api.ExchangeRateApi

class ExhangeRateSingleton {
    fun newInstance() : ExchangeRateApi{
        return RetrofitSingleton.getInstance().create(ExchangeRateApi::class.java)
    }
}