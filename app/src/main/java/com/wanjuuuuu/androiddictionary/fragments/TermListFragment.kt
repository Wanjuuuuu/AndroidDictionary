package com.wanjuuuuu.androiddictionary.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.wanjuuuuu.androiddictionary.R
import com.wanjuuuuu.androiddictionary.adapters.TermAdapter
import com.wanjuuuuu.androiddictionary.data.Term
import com.wanjuuuuu.androiddictionary.databinding.FragmentTermListBinding
import com.wanjuuuuu.androiddictionary.utils.Injector
import com.wanjuuuuu.androiddictionary.viewmodels.TermListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TermListFragment : Fragment() {

    private lateinit var binding: FragmentTermListBinding
    private val viewModel: TermListViewModel by viewModels {
        Injector.provideTermListViewModelFactory(requireContext())
    }
    private val updatingTermRepository by lazy {
        Injector.getUpdatingTermRepository(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpFragment()
        binding = FragmentTermListBinding.inflate(inflater, container, false)

        val termAdapter = TermAdapter({ term -> onClickTermItem(term) },
            { id, bookmarked -> onClickBookmark(id, bookmarked) })
        binding.termList.adapter = termAdapter

        observeData(termAdapter)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_term_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_bookmark -> {
                with(viewModel) {
                    filterTermsByBookmark(reverseFilter())
                }
                observeData(binding.termList.adapter as TermAdapter)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpFragment() {
        setHasOptionsMenu(true)
    }

    private fun observeData(adapter: TermAdapter) {
        viewModel.terms.observe(
            viewLifecycleOwner,
            Observer { result -> adapter.submitList(result) })
    }

    private fun onClickTermItem(term: Term) {
        val termDetailFragment = TermDetailFragment(term.id)
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.dictionary_container, termDetailFragment)
            addToBackStack(null)
        }.commit()
    }

    private fun onClickBookmark(id: Long, bookmarked: Boolean) {
        lifecycleScope.launch(Dispatchers.Default) {
            updatingTermRepository.setTermBookmarked(id, bookmarked)
        }
    }
}