package com.wanjuuuuu.androiddictionary.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.wanjuuuuu.androiddictionary.R
import com.wanjuuuuu.androiddictionary.adapters.TermAdapter
import com.wanjuuuuu.androiddictionary.databinding.FragmentTermListBinding
import com.wanjuuuuu.androiddictionary.utils.Injector
import com.wanjuuuuu.androiddictionary.utils.TAG
import com.wanjuuuuu.androiddictionary.viewmodels.TermListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TermListFragment : Fragment() {

    private lateinit var binding: FragmentTermListBinding
    private val termListViewModel: TermListViewModel by viewModels {
        Injector.provideTermListViewModelFactory(this)
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

        val termAdapter = TermAdapter { id, bookmarked -> onClickBookmark(id, bookmarked) }
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
                termListViewModel.reverseFilter()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpFragment() {
        setHasOptionsMenu(true)
    }

    private fun observeData(adapter: TermAdapter) {
        termListViewModel.run {
            terms.observe(viewLifecycleOwner, Observer { result ->
                adapter.submitList(result)

                //trial
                lifecycleScope.launch {
                    val termsInCategory = launchTermsInCategory()
                    Log.d(TAG, "termsInCategory = $termsInCategory")
                }
            })
        }
    }

    private fun onClickBookmark(id: Long, bookmarked: Boolean) {
        lifecycleScope.launch(Dispatchers.Default) {
            updatingTermRepository.setTermBookmarked(id, bookmarked)
        }
    }
}