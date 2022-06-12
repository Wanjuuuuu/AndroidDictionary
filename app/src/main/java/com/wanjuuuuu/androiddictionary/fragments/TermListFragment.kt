package com.wanjuuuuu.androiddictionary.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.wanjuuuuu.androiddictionary.R
import com.wanjuuuuu.androiddictionary.adapters.ListGroupAdapter
import com.wanjuuuuu.androiddictionary.data.ListGroup
import com.wanjuuuuu.androiddictionary.databinding.FragmentTermListBinding
import com.wanjuuuuu.androiddictionary.utils.Injector
import com.wanjuuuuu.androiddictionary.viewmodels.TermListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class TermListFragment : Fragment() {

    private lateinit var binding: FragmentTermListBinding
    private val termListViewModel: TermListViewModel by viewModels {
        Injector.provideTermListViewModelFactory(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpFragment()
        binding = FragmentTermListBinding.inflate(inflater, container, false)

        val termAdapter =
            ListGroupAdapter({ listGroup -> onClickCollapse(listGroup) },
                { id, bookmarked -> onClickBookmark(id, bookmarked) })
        binding.termGroup.adapter = termAdapter

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

    private fun observeData(adapter: ListGroupAdapter) {
        termListViewModel.run {
            categorizedTerms.observe(
                viewLifecycleOwner,
                Observer { adapter.submitList(it) })
        }
    }

    private fun onClickBookmark(id: Long, bookmarked: Boolean) {
        termListViewModel.updateBookmark(id, bookmarked)
    }

    private fun onClickCollapse(listGroup: ListGroup) {
        termListViewModel.expandOrCollapseList(listGroup)
    }
}