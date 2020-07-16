package com.wanjuuuuu.androiddictionary.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wanjuuuuu.androiddictionary.data.TermRepository

class TermDetailViewModelFactory(
    private val termRepository: TermRepository,
    private val termId: Long
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TermDetailViewModel(termRepository, termId) as T
    }
}