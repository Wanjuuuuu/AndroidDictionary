package com.wanjuuuuu.androiddictionary.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wanjuuuuu.androiddictionary.data.GettingTermRepository

class TermListViewModelFactory(private val gettingTermRepository: GettingTermRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TermListViewModel(gettingTermRepository) as T
    }
}