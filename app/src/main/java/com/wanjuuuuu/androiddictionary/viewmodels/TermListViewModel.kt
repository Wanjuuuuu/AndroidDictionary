package com.wanjuuuuu.androiddictionary.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.wanjuuuuu.androiddictionary.data.GettingTermRepository
import com.wanjuuuuu.androiddictionary.data.Term
import com.wanjuuuuu.androiddictionary.data.UpdatingTermRepository
import com.wanjuuuuu.androiddictionary.utils.TAG
import kotlinx.coroutines.launch

class TermListViewModel(
    private val gettingTermRepository: GettingTermRepository,
    private val updatingTermRepository: UpdatingTermRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val BOOKMARK_SAVED_STATE_KEY = "BOOKMARK_SAVED_STATE_KEY"
    }

    val terms = isFilteredByBookmark().switchMap {
        if (it) gettingTermRepository.getBookmarkedTerms()
        else gettingTermRepository.getAllTerms()
    }

    val categorizedTerms = terms.switchMap { gettingTermRepository.getCategorizedTerms(it) }

    fun updateBookmark(id: Long, bookmarked: Boolean) {
        viewModelScope.launch {
            updatingTermRepository.setTermBookmarked(id, bookmarked)
        }
    }

    fun reverseFilter() {
        val isFiltered = savedStateHandle.get(BOOKMARK_SAVED_STATE_KEY) ?: false
        savedStateHandle.set(BOOKMARK_SAVED_STATE_KEY, !isFiltered)
    }

    private fun isFilteredByBookmark(): MutableLiveData<Boolean> {
        return savedStateHandle.getLiveData(BOOKMARK_SAVED_STATE_KEY, false)
    }
}