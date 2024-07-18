package com.example.converter.features.main.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converter.BuildConfig
import com.example.converter.data.repositories.ExchangeCurrensiesRepository
import kotlinx.coroutines.launch
const val API_KEY =  BuildConfig.API_KEY
class MainViewModel : ViewModel() {
    private val _currencies = MutableLiveData<String>()
    val currencies: LiveData<String> = _currencies

    private val _course = MutableLiveData<List<String>>()
    val course: LiveData<List<String>> = _course

    private val _value = MutableLiveData<Double>()
    val value: LiveData<Double> = _value

    private val _baseCurrency = MutableLiveData<String>()
    val baseCurrency: LiveData<String> = _baseCurrency

    private val _targetCurrency = MutableLiveData<String>()
    val targetCurrency: LiveData<String> = _targetCurrency

    fun loadCurrencies(basecurrency:String, currency: String, value: Double) {
        _value.value = value
        _baseCurrency.value = basecurrency
        _targetCurrency.value = currency
        viewModelScope.launch {
            try {
                val response = ExchangeCurrensiesRepository().getCurrencies(
                    baseCurrency = basecurrency,
                    currencies = currency,
                    apiKey = API_KEY
                )
                val usdValue = response.data?.get(currency)?.value ?: 1.0
                val result = usdValue * value
                _currencies.postValue(result.toString())
            } catch (e: Exception) {
                Log.e("API_ERROR", "Ошибка API: ${e.message}", e)
                _currencies.postValue("000")
            }
        }
    }
    fun course() {
        viewModelScope.launch {
            try {
                val response = ExchangeCurrensiesRepository().getCurrencies("RUB", "USD,EUR,CHF", null, API_KEY)
                val courses = response.data?.entries?.map { (currencyCode, currencyData) ->
                    val rate = currencyData.value
                    "${String.format("%.1f", 1 / rate)}"
                } ?: emptyList()
                _course.postValue(courses)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Ошибка API: ${e.message}", e)
            }
        }
    }

}
