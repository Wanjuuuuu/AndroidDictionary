package com.wanjuuuuu.androiddictionary.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanjuuuuu.androiddictionary.data.GettingTermRepository
import com.wanjuuuuu.androiddictionary.data.Term
import kotlinx.coroutines.launch

class TermDetailViewModel(gettingTermRepository: GettingTermRepository, termId: Long) :
    ViewModel() {

    val term = gettingTermRepository.getTerm(termId)
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    private val _isRefreshing = MutableLiveData(false)

    fun launchDataRefreshIfNeeded(block: suspend (Term?) -> Unit) {
        if (needRefresh()) {
            viewModelScope.launch {
                try {
                    _isRefreshing.value = true
                    block(term.value)
                } catch (error: Throwable) {
                } finally {
                    _isRefreshing.value = false
                }
            }
        }
    }

    private fun needRefresh(): Boolean {
        return term.value?.needRescraping ?: false
    }
}