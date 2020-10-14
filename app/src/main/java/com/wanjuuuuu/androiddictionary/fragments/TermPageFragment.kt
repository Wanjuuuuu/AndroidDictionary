package com.wanjuuuuu.androiddictionary.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.wanjuuuuu.androiddictionary.databinding.FragmentTermPageBinding
import com.wanjuuuuu.androiddictionary.utils.ANDROID_REFERENCE_BASE_URL

class TermPageFragment : Fragment() {

    private val args: TermPageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTermPageBinding.inflate(inflater, container, false).apply {
            termPage.loadUrl("$ANDROID_REFERENCE_BASE_URL${args.termUrl}")
        }
        return binding.root
    }
}