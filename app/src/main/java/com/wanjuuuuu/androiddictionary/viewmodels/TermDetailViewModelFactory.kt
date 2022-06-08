package com.wanjuuuuu.androiddictionary.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wanjuuuuu.androiddictionary.data.GettingTermRepository
import com.wanjuuuuu.androiddictionary.data.UpdatingTermRepository

class TermDetailViewModelFactory(
    private val gettingTermRepository: GettingTermRepository,
    private val updatingTermRepository: UpdatingTermRepository,
    private val termId: Long
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TermDetailViewModel(gettingTermRepository, updatingTermRepository, termId) as T
    }
}