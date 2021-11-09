package com.wanjuuuuu.androiddictionary.viewmodels

import androidx.lifecycle.*
import com.wanjuuuuu.androiddictionary.data.GettingTermRepository
import com.wanjuuuuu.androiddictionary.data.Term
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    suspend fun launchTermsInCategory(): Map<String, List<Term>> {
        return withContext(Dispatchers.Default) {
            terms.value?.groupBy { it.category } ?: hashMapOf()
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