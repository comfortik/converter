package com.example.converter.features.main.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.converter.R
import com.example.converter.databinding.FragmentMainBinding
import com.example.converter.features.main.presentation.MainViewModel
import com.example.converter.features.result.ui.ResultFragment
import com.example.converter.features.utils.Currencies


class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding
    private var flag = false

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
        initListeners()
        restoreState()
        initObservers()
        setAdapters()
        setCourse()

    }
    private fun setAdapters(){
        val currencies =Currencies.values().map { it.name }
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_drop_down, currencies)
        binding.spinner.setAdapter(adapter)
        binding.spinnerTwo.setAdapter(adapter)
    }
    private fun initObservers() {
        viewModel.currencies.observe(viewLifecycleOwner) { currencies ->
            if(!flag){
                val action = MainFragmentDirections.actionMainFragmentToResultFragment(currencies)
                findNavController().navigate(action)
                flag = true
            }
        }
        viewModel.value.observe(viewLifecycleOwner) {
            binding.etInput.setText(it.toString())
        }
        viewModel.course.observe(viewLifecycleOwner) {
            binding.dollar.text = it[2]
            binding.tvEuro.text = it[1]
            binding.tvFrank.text = it[0]
        }
        viewModel.baseCurrency.observe(viewLifecycleOwner) {
            binding.spinner.setText(it, false)
        }
        viewModel.targetCurrency.observe(viewLifecycleOwner) {
            binding.spinnerTwo.setText(it, false)
        }
    }
    private fun initListeners(){
        binding.btnConvert.setOnClickListener {
            if (binding.etInput.text.isNotEmpty()) {
                val baseCurrency = binding.spinner.text.toString()
                val currency = binding.spinnerTwo.text.toString()
                val value = binding.etInput.text.toString().toDouble()
                viewModel.loadCurrencies(baseCurrency, currency, value)
                flag = false
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
    private fun restoreState() {
        try{
            val baseCur = viewModel.baseCurrency.value!!
            binding.spinner.setText(baseCur, false)
            val targetCur = viewModel.targetCurrency.value!!
            binding.spinner.setText(targetCur, false)
        }catch (e: NullPointerException){

        }

        viewModel.value.value?.let {
            binding.etInput.setText(it.toString())
        }
    }
    override fun onResume() {
        super.onResume()
        setAdapters()
    }


}
