package com.wanjuuuuu.androiddictionary.viewmodels

import androidx.lifecycle.ViewModel
import com.wanjuuuuu.androiddictionary.data.GettingTermRepository

class TermListViewModel(gettingTermRepository: GettingTermRepository) : ViewModel() {
    val terms = gettingTermRepository.getAllTerms()
}