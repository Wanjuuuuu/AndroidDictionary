package com.wanjuuuuu.androiddictionary.viewmodels

import androidx.lifecycle.*
import com.wanjuuuuu.androiddictionary.data.GettingTermRepository
import com.wanjuuuuu.androiddictionary.data.Term
import com.wanjuuuuu.androiddictionary.data.UpdatingTermRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class TermListViewModel(
    private val gettingTermRepository: GettingTermRepository,
    private val updatingTermRepository: UpdatingTermRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val BOOKMARK_SAVED_STATE_KEY = "BOOKMARK_SAVED_STATE_KEY"
    }

    val terms = isFilteredByBookmark().asLiveData().switchMap {
        if (it) gettingTermRepository.getBookmarkedTerms().asLiveData()
        else gettingTermRepository.getAllTerms().asLiveData()
    }

    class CategorizedTerms(val map: Map<String, List<Term>> = mapOf())

    private val _categorizedTerms = MutableStateFlow(CategorizedTerms())
    val categorizedTerms: LiveData<CategorizedTerms> = _categorizedTerms.asLiveData()

    private val categorizingFlow = isFilteredByBookmark().map {
        if (it) gettingTermRepository.getBookmarkedTerms()
        else gettingTermRepository.getAllTerms()
    }.flatMapLatest {
        gettingTermRepository.getCategorizedTerms(it)
    }

    init {
        viewModelScope.launch {
            categorizingFlow.collect {
                _categorizedTerms.value = CategorizedTerms(it)
            }
        }
    }

    fun updateBookmark(id: Long, bookmarked: Boolean) {
        viewModelScope.launch {
            updatingTermRepository.setTermBookmarked(id, bookmarked)
        }
    }

    fun reverseFilter() {
        val isFiltered = savedStateHandle.get(BOOKMARK_SAVED_STATE_KEY) ?: false
        savedStateHandle.set(BOOKMARK_SAVED_STATE_KEY, !isFiltered)
    }

    private fun isFilteredByBookmark(): Flow<Boolean> {
        return savedStateHandle.getLiveData(BOOKMARK_SAVED_STATE_KEY, false).asFlow()
    }
}