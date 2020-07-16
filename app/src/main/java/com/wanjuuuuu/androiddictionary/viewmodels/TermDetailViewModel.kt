package com.wanjuuuuu.androiddictionary.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanjuuuuu.androiddictionary.data.TermRepository

class TermDetailViewModel(termRepository: TermRepository, termId: Long) : ViewModel() {
    val term = termRepository.getTerm(termId, viewModelScope)
}