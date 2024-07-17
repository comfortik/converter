package com.example.converter.features.main.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.converter.R
import com.example.converter.databinding.FragmentMainBinding
import com.example.converter.features.main.presentation.MainViewModel
import com.example.converter.features.result.ui.ResultFragment


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initListeners()
        setAdapters()
        setCourse()
    }
    private fun setAdapters(){
        val currencies = listOf("USD", "EUR", "RUB", "JPY", "GBP", "AUD", "CAD", "CHF", "CNY", "HKD", "NZD", "AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AWG", "AZN", "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BRL", "BSD", "BTN", "BWP", "BYN", "BYR", "BZD", "CAD", "CDF", "CHF", "CLF", "CLP", "CNY", "COP", "CRC", "CUC", "CUP", "CVE", "CZK", "DJF", "DKK", "DOP", "DZD", "EGP", "ERN", "ETB", "EUR", "FJD", "FKP", "GBP", "GEL", "GGP", "GHS", "GIP", "GMD", "GNF", "GTQ", "GTY", "GYD", "HKD", "HNL", "HRK", "HTG", "HUF", "IDR", "ILS", "IMP", "INR", "IQD", "IRR", "ISK", "JEP", "JMD", "JOD", "JPY", "KES", "KGS", "KHR", "KMF", "KPW", "KRW", "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LTL", "LVL", "LYD", "MAD", "MDL", "MGA", "MKD", "MMK", "MNT", "MOP", "MRO", "MUR", "MVR", "MWK", "MXN", "MYR", "MZN", "NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF", "SAR", "SBD", "SCR", "SDG", "SEK", "SGD", "SHP", "SLL", "SOS", "SRD", "STD", "SVC", "SYP", "SZL", "THB", "TJS", "TMT", "TND", "TOP", "TRY", "TTD", "TWD", "TZS", "UAH", "UGX", "USD", "UYU", "UZS", "VEF", "VND", "VUV", "WST", "XAF", "XAG", "XAU", "XCD", "XDR", "XOF", "XPF", "YER", "ZAR", "ZMK", "ZMW", "ZWL", "XPT", "XPD", "BTC", "ETH", "BNB", "XRP", "SOL", "DOT", "AVAX", "MATIC", "LTC", "ADA")
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_drop_down, currencies)
        binding.spinner.setAdapter(adapter)
        binding.spinnerTwo.setAdapter(adapter)
    }
    private fun initObservers() {
        viewModel.currencies.observe(viewLifecycleOwner) {
            val fragment = ResultFragment.newInstance(it.toString())
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .addToBackStack(null)
                .commit()
        }
        viewModel.course.observe(viewLifecycleOwner){
            binding.dollar.text= it.get(2)
            binding.tvEuro.text = it.get(1)
            binding.tvFrank.text = it.get(0)
        }
    }
    private fun initListeners(){
        binding.btnConvert.setOnClickListener {
            if (binding.etInput.text.isNotEmpty()) {
                val baseCurrency = binding.spinner.text.toString()
                val currency = binding.spinnerTwo.text.toString()
                val value = binding.etInput.text.toString().toDouble()
                Log.d("asd", currency)
                viewModel.loadCurrencies(baseCurrency, currency, value)
            }
        }
        binding.reverse.setOnClickListener {
            val saveSelectedItem = binding.spinner.text.toString()
            binding.spinner.setText(binding.spinnerTwo.text.toString(), false)
            binding.spinnerTwo.setText(saveSelectedItem, false)
        }


    }
    private fun setCourse(){
        viewModel.course()
    }

}
