package com.wanjuuuuu.androiddictionary.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanjuuuuu.androiddictionary.data.GettingTermRepository
import com.wanjuuuuu.androiddictionary.data.Term
import com.wanjuuuuu.androiddictionary.data.UpdatingTermRepository
import kotlinx.coroutines.launch

class TermDetailViewModel(
    gettingTermRepository: GettingTermRepository,
    private val updatingTermRepository: UpdatingTermRepository,
    termId: Long
) : ViewModel() {

    val term = gettingTermRepository.getTerm(termId)
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    private val _isRefreshing = MutableLiveData(false)

    fun launchDataRefreshIfNeeded(term: Term) {
        if (term.needRescraping) {
            viewModelScope.launch {
                try {
                    _isRefreshing.value = true
                    updatingTermRepository.refreshTermDescription(term)
                } catch (error: Throwable) {
                } finally {
                    _isRefreshing.value = false
                }
            }
        }
    }

    fun updateBookmark(id: Long, bookmarked: Boolean) {
        viewModelScope.launch {
            updatingTermRepository.setTermBookmarked(id, bookmarked)
        }
    }
}