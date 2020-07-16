package com.wanjuuuuu.androiddictionary.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wanjuuuuu.androiddictionary.data.TermRepository

class TermListViewModelFactory(private val termRepository: TermRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TermListViewModel(termRepository) as T
    }
}