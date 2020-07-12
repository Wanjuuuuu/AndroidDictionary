package com.wanjuuuuu.androiddictionary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.wanjuuuuu.androiddictionary.data.Term
import com.wanjuuuuu.androiddictionary.databinding.FragmentTermDetailBinding
import com.wanjuuuuu.androiddictionary.viewmodels.TermDetailViewModel

class TermDetailFragment(term: Term) : Fragment() {

    private lateinit var binding: FragmentTermDetailBinding
    private val viewModel by lazy { TermDetailViewModel(requireContext(), term) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTermDetailBinding.inflate(inflater, container, false)

        submitData()

        return binding.root
    }

    private fun submitData() {
        viewModel.term.observe(viewLifecycleOwner, Observer { result -> binding.term = result })
    }
}