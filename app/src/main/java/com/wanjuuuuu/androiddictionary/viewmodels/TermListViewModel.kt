package com.wanjuuuuu.androiddictionary.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanjuuuuu.androiddictionary.data.TermRepository

class TermListViewModel(termRepository: TermRepository) : ViewModel() {
    val terms = termRepository.getTerms(viewModelScope)
}