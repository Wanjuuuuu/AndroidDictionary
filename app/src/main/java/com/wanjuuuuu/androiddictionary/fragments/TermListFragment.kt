package com.wanjuuuuu.androiddictionary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.wanjuuuuu.androiddictionary.R
import com.wanjuuuuu.androiddictionary.adapters.TermAdapter
import com.wanjuuuuu.androiddictionary.data.Term
import com.wanjuuuuu.androiddictionary.databinding.FragmentTermListBinding
import com.wanjuuuuu.androiddictionary.viewmodels.TermListViewModel

class TermListFragment : Fragment() {

    private lateinit var binding: FragmentTermListBinding
    private val viewModel: TermListViewModel by lazy { TermListViewModel(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTermListBinding.inflate(inflater, container, false)

        val termAdapter = TermAdapter { term -> onClickTermItem(term) }
        binding.termList.adapter = termAdapter

        submitData(termAdapter)

        return binding.root
    }

    private fun submitData(adapter: TermAdapter) {
        viewModel.terms.observe(
            viewLifecycleOwner,
            Observer { result -> adapter.submitList(result) })
    }

    private fun onClickTermItem(term: Term) {
        val termDetailFragment = TermDetailFragment(term)
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.dictionary_container, termDetailFragment)
            addToBackStack(null)
        }.commit()
    }
}