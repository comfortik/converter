package com.example.converter.features.result.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.converter.R
import com.example.converter.databinding.FragmentResultBinding
import com.example.converter.features.main.ui.MainFragment

class ResultFragment : Fragment() {
    private lateinit var binding : FragmentResultBinding
    private lateinit var callback: OnBackPressedCallback
    companion object {
        private const val ARG_RESULT = "result"
        fun newInstance(result:String) = ResultFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_RESULT, result)
            }
        }
    }
    private var result: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            result = it.getString(ARG_RESULT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvResult.text = "Result:\n${result}"
        binding.cross.setOnClickListener {
            findNavController().navigateUp()
        }
        callback = object : OnBackPressedCallback(true ) {
            override fun handleOnBackPressed() {
                    findNavController().navigateUp()

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        callback.remove()
    }

}