package com.wanjuuuuu.androiddictionary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.wanjuuuuu.androiddictionary.databinding.FragmentTermDetailBinding
import com.wanjuuuuu.androiddictionary.utils.Injector
import com.wanjuuuuu.androiddictionary.viewmodels.TermDetailViewModel
import kotlinx.coroutines.launch

class TermDetailFragment : Fragment() {

    private val args: TermDetailFragmentArgs by navArgs()
    private val termDetailViewModel: TermDetailViewModel by viewModels {
        Injector.provideTermDetailViewModelFactory(requireContext(), args.termId)
    }
    private val updatingTermRepository by lazy {
        Injector.getUpdatingTermRepository(requireContext())
    }

    private lateinit var binding: FragmentTermDetailBinding

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
                updatingTermRepository.setTermBookmarked(args.termId, it.isSelected)
            }
        }
    }

    private fun observeDataRefresher() {
        termDetailViewModel.run {
            term.observe(
                viewLifecycleOwner,
                Observer {
                    launchDataRefreshIfNeeded {
                        it?.let { updatingTermRepository.refreshTermDescription(it) }
                    }
                })
        }
    }
}