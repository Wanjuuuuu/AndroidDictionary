package com.wanjuuuuu.androiddictionary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wanjuuuuu.androiddictionary.adapters.TermAdapter
import com.wanjuuuuu.androiddictionary.data.Term
import com.wanjuuuuu.androiddictionary.databinding.FragmentTermListBinding

class TermListFragment : Fragment() {

    private lateinit var binding: FragmentTermListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTermListBinding.inflate(inflater, container, false)

        val termAdapter = TermAdapter()
        binding.termList.adapter = termAdapter

        // TODO : change into real data
        submitMockData(termAdapter)

        return binding.root
    }

    private fun submitMockData(adapter: TermAdapter) {
        val term = Term("name", "url/url")
        adapter.submitList(listOf(term))
    }
}