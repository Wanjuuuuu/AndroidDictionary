package com.wanjuuuuu.androiddictionary.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.wanjuuuuu.androiddictionary.R
import com.wanjuuuuu.androiddictionary.databinding.FragmentTermDetailBinding
import com.wanjuuuuu.androiddictionary.utils.ANDROID_REFERENCE_BASE_URL
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
        initBookmarkClickListener()
        initTitleClickListener()

        observeDataRefresher()

        return binding.root
    }

    private fun initBookmarkClickListener() {
        binding.bookmarkClickListener = View.OnClickListener {
            it.run { isSelected = !isSelected }
            lifecycleScope.launch {
                updatingTermRepository.setTermBookmarked(args.termId, it.isSelected)
            }
        }
    }

    private fun initTitleClickListener() {
        binding.termTitleClickListener = View.OnClickListener {
            val term = termDetailViewModel.term.value ?: return@OnClickListener
            val fullUrl = "$ANDROID_REFERENCE_BASE_URL${term.url}"
            launchCustomTabs(fullUrl)
        }
    }

    private fun launchCustomTabs(url: String) {
        val customTabsIntent = buildCustomTabsIntent()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
    }

    private fun buildCustomTabsIntent(): CustomTabsIntent {
        return with(CustomTabsIntent.Builder()) {
            setStartAnimations(requireContext(), R.anim.slide_in_right, R.anim.slide_out_left)
            setExitAnimations(requireContext(), R.anim.slide_in_left, R.anim.slide_out_right)
            setDefaultColorSchemeParams(buildCustomTabColorSchemeParams())
            build()
        }
    }

    private fun buildCustomTabColorSchemeParams(): CustomTabColorSchemeParams {
        return with(CustomTabColorSchemeParams.Builder()) {
            setToolbarColor(
                ResourcesCompat.getColor(
                    requireContext().resources,
                    R.color.colorPrimary,
                    null
                )
            )
            build()
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