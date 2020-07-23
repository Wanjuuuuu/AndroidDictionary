package com.wanjuuuuu.androiddictionary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.wanjuuuuu.androiddictionary.databinding.FragmentTermDetailBinding
import com.wanjuuuuu.androiddictionary.utils.Injector
import com.wanjuuuuu.androiddictionary.viewmodels.TermDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TermDetailFragment(termId: Long) : Fragment() {

    private lateinit var binding: FragmentTermDetailBinding
    private val viewModel: TermDetailViewModel by viewModels {
        Injector.provideTermDetailViewModelFactory(requireContext(), termId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTermDetailBinding.inflate(inflater, container, false)

        submitData()
        updateDataAsync()

        return binding.root
    }

    private fun submitData() {
        viewModel.term.observe(viewLifecycleOwner, Observer { result -> binding.term = result })
    }

    private fun updateDataAsync() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.updateTermDescription()
        }
    }
}