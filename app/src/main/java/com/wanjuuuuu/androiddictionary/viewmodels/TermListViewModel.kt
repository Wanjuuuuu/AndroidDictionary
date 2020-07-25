package com.wanjuuuuu.androiddictionary.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.wanjuuuuu.androiddictionary.data.GettingTermRepository

class TermListViewModel(
    gettingTermRepository: GettingTermRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val BOOKMARK_SAVED_STATE_KEY = "BOOKMARK_SAVED_STATE_KEY"
    }

    val terms = isFilteredByBookmark().switchMap {
        if (it) gettingTermRepository.getBookmarkedTerms()
        else gettingTermRepository.getAllTerms()
    }

    fun reverseFilter() {
        val isFiltered = savedStateHandle.get(BOOKMARK_SAVED_STATE_KEY) ?: false
        savedStateHandle.set(BOOKMARK_SAVED_STATE_KEY, !isFiltered)
    }

    private fun isFilteredByBookmark(): MutableLiveData<Boolean> {
        return savedStateHandle.getLiveData(BOOKMARK_SAVED_STATE_KEY, false)
    }
}