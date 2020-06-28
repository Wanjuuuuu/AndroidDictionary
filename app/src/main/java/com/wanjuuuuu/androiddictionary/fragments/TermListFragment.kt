package com.wanjuuuuu.androiddictionary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wanjuuuuu.androiddictionary.adapters.TermAdapter
import com.wanjuuuuu.androiddictionary.databinding.FragmentTermListBinding
import com.wanjuuuuu.androiddictionary.viewmodels.TermListViewModel

class TermListFragment : Fragment() {

    private lateinit var binding: FragmentTermListBinding
    private val viewModel: TermListViewModel = TermListViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTermListBinding.inflate(inflater, container, false)

        val termAdapter = TermAdapter()
        binding.termList.adapter = termAdapter

        submitData(termAdapter)

        return binding.root
    }

    private fun submitData(adapter: TermAdapter) {
        adapter.submitList(viewModel.terms)
    }
}