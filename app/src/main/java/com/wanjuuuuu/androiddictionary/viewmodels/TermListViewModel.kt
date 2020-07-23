package com.wanjuuuuu.androiddictionary.viewmodels

import androidx.lifecycle.ViewModel
import com.wanjuuuuu.androiddictionary.data.TermRepository

class TermListViewModel(termRepository: TermRepository) : ViewModel() {
    val terms = termRepository.getAllTerms()
}