package com.example.converter.features.main.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converter.data.repositories.ExchangeCurrensiesRepository
import kotlinx.coroutines.launch
const val API_KEY =  "cur_live_ccIQyzRNHGY97e8bzLgAJfFe8nql16TZSyucys0P"
class MainViewModel : ViewModel() {
    private val _currencies = MutableLiveData<String>()
    val currencies: LiveData<String> = _currencies
    private val _course = MutableLiveData<List<String>>()
    val course: LiveData<List<String>> = _course

    fun loadCurrencies(basecurrency:String, currency: String, value: Double) {
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
