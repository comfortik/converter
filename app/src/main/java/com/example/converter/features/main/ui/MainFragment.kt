package com.example.converter.features.main.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.converter.R
import com.example.converter.databinding.FragmentMainBinding
import com.example.converter.features.main.presentation.MainViewModel

const val API_KEY = "cur_live_ccIQyzRNHGY97e8bzLgAJfFe8nql16TZSyucys0P"

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
        viewModel.currencies.observe(viewLifecycleOwner) {
            binding.tvResult.text = it.toString()
        }
        binding.btnConvert.setOnClickListener {
            if (binding.etInput.text.isNotEmpty()) {
                val currency = binding.spinner.selectedItem.toString()
                val value = binding.etInput.text.toString().toDouble()
                Log.d("asd", currency)
                viewModel.loadCurrencies(currency, value)
            }
        }
    }

}
