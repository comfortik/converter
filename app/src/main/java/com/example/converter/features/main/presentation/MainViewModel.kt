package com.example.converter.features.main.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.converter.data.repositories.ExchangeCurrensiesRepository
import com.example.converter.data.retrofit.ExhangeRateSingleton
import com.example.converter.domain.model.CurrencyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _currencies = MutableLiveData<String>()
    val currencies: LiveData<String> = _currencies

    fun loadCurrencies(currency: String, value: Double) {
        val apiKey = "cur_live_ccIQyzRNHGY97e8bzLgAJfFe8nql16TZSyucys0P"
        val currenciesParam = currency

        ExchangeCurrensiesRepository().getCurrencies(
            baseCurrency = "RUB",
            currencies = currenciesParam,
            apiKey = apiKey
        ).enqueue(object : Callback<CurrencyResponse> {
            override fun onResponse(
                call: Call<CurrencyResponse>,
                response: Response<CurrencyResponse>
            ) {
                if (response.isSuccessful) {
                    val currencyResponse = response.body()
                    val usdValue = currencyResponse?.data?.get(currency)?.value ?: 1.0
                    val result = usdValue * value
                    _currencies.value = result.toString()
                } else {
                    Log.e("API_ERROR", "Ошибка API: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<CurrencyResponse>, t: Throwable) {
                Log.e("NETWORK_ERROR", "Сетевая ошибка: ${t.message}", t)
            }
        })
    }
}
