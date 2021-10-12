package com.wanjuuuuu.androiddictionary.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.wanjuuuuu.androiddictionary.databinding.FragmentTermPageBinding
import com.wanjuuuuu.androiddictionary.utils.ANDROID_DEVELOPER_HOST
import com.wanjuuuuu.androiddictionary.utils.ANDROID_REFERENCE_BASE_URL

class TermPageFragment : Fragment() {

    private val args: TermPageFragmentArgs by navArgs()
    private val webViewUrlHandler = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val uri = request?.run { Uri.parse(url.toString()) }
            return routeToBrowser(uri)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            val uri = url?.let { Uri.parse(it) }
            return routeToBrowser(uri)
        }
    }

    private fun routeToBrowser(uri: Uri?): Boolean {
        uri?.run {
            if (host == ANDROID_DEVELOPER_HOST) return false
            Intent(Intent.ACTION_VIEW, uri).apply {
                startActivity(this)
            }
        }
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTermPageBinding.inflate(inflater, container, false).apply {
            setUpTermPage(termPage)
        }
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpTermPage(webView: WebView) {
        with(webView) {
            settings.setSupportZoom(false)
            settings.javaScriptEnabled = true
            webViewClient = webViewUrlHandler
            loadUrl("$ANDROID_REFERENCE_BASE_URL${args.termUrl}")
        }
    }
}