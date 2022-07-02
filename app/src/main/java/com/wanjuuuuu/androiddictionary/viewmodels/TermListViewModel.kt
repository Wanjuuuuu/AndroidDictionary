package com.wanjuuuuu.androiddictionary.viewmodels

import androidx.lifecycle.*
import com.wanjuuuuu.androiddictionary.data.GettingTermRepository
import com.wanjuuuuu.androiddictionary.data.ListGroup
import com.wanjuuuuu.androiddictionary.data.ListGroupTransformer
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
        private const val COLLAPSED_CATEGORIES_KEY = "COLLAPSED_CATEGORIES_KEY"
    }

    val terms = isFilteredByBookmark().asLiveData().switchMap {
        if (it) gettingTermRepository.getBookmarkedTerms().asLiveData()
        else gettingTermRepository.getAllTerms().asLiveData()
    }

    private val _categorizedTerms = MutableStateFlow(listOf<ListGroup>())
    val categorizedTerms: LiveData<List<ListGroup>> = _categorizedTerms.asLiveData()

    private val categorizingFlow = isFilteredByBookmark().map {
        if (it) gettingTermRepository.getBookmarkedTerms()
        else gettingTermRepository.getAllTerms()
    }.mapLatest {
        gettingTermRepository.getCategorizedTerms(it)
    }.flatMapLatest {
        ListGroupTransformer.transform(it, getCategoriesCollapsed())
    }

    init {
        viewModelScope.launch {
            categorizingFlow.collectLatest {
                _categorizedTerms.value = it
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

    fun expandOrCollapseCategory(listGroup: ListGroup) {
        val toggled = toggleCategoryCollapsed(listGroup)
        val terms = _categorizedTerms.value.toList()
        _categorizedTerms.value =
            terms.map { if (it.category == listGroup.category) it.setCollapsed(toggled) else it }
    }

    private fun toggleCategoryCollapsed(listGroup: ListGroup): Boolean {
        val set = getCategoriesCollapsed().toMutableSet()
        val toggled = !set.contains(listGroup.category)
        if (toggled) {
            set.add(listGroup.category)
        } else {
            set.remove(listGroup.category)
        }
        setCategoriesCollapsed(set)
        return toggled
    }

    private fun getCategoriesCollapsed(): Set<String> {
        val list = savedStateHandle.get<List<String>>(COLLAPSED_CATEGORIES_KEY) ?: listOf()
        return list.toSet()
    }

    private fun setCategoriesCollapsed(set: Set<String>) {
        savedStateHandle.set(COLLAPSED_CATEGORIES_KEY, set.toList())
    }
}