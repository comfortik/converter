package com.example.converter.features.main.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converter.data.repositories.ExchangeCurrensiesRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _currencies = MutableLiveData<String>()
    val currencies: LiveData<String> = _currencies

    fun loadCurrencies(basecurrency:String, currency: String, value: Double) {
        val apiKey = "cur_live_ccIQyzRNHGY97e8bzLgAJfFe8nql16TZSyucys0P"

        viewModelScope.launch {
            try {
                val response = ExchangeCurrensiesRepository().getCurrencies(
                    baseCurrency = basecurrency,
                    currencies = currency,
                    apiKey = apiKey
                )
                val usdValue = response.data?.get(currency)?.value ?: 1.0
                val result = usdValue * value
                _currencies.postValue(result.toString())
            } catch (e: Exception) {
                Log.e("API_ERROR", "Ошибка API: ${e.message}", e)
            }
        }
    }
}
