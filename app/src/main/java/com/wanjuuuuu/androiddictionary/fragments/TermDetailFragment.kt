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
import kotlinx.coroutines.launch

class TermDetailFragment : Fragment() {

    companion object {
        private const val TERM_ID = "TERM_DETAIL_FRAGMENT_ID"

        fun newInstance(termId: Long): Fragment {
            val args = Bundle()
            args.putLong(TERM_ID, termId)

            val fragment = TermDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: FragmentTermDetailBinding
    private val termDetailViewModel: TermDetailViewModel by viewModels {
        Injector.provideTermDetailViewModelFactory(requireContext(), getTermId())
    }
    private val updatingTermRepository by lazy {
        Injector.getUpdatingTermRepository(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTermDetailBinding.inflate(inflater, container, false).apply {
            viewModel = termDetailViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        initBookmarkButton()

        observeDataRefresher()

        return binding.root
    }

    private fun initBookmarkButton() {
        binding.bookmarkClickListener = View.OnClickListener {
            it.apply { isSelected = !isSelected }
            lifecycleScope.launch {
                updatingTermRepository.setTermBookmarked(
                    getTermId(),
                    it.isSelected
                )
            }
        }
    }

    private fun observeDataRefresher() {
        termDetailViewModel.run {
            term.observe(
                viewLifecycleOwner,
                Observer {
                    launchDataRefreshIfNeeded {
                        if (it != null) updatingTermRepository.refreshTermDescription(it)
                    }
                })
        }
    }

    private fun getTermId(): Long = requireArguments().getLong(TERM_ID)
}