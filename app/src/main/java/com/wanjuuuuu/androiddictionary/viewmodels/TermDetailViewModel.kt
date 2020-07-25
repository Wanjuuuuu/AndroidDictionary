package com.wanjuuuuu.androiddictionary.viewmodels

import androidx.lifecycle.ViewModel
import com.wanjuuuuu.androiddictionary.data.GettingTermRepository

class TermDetailViewModel(gettingTermRepository: GettingTermRepository, termId: Long) :
    ViewModel() {
    val term = gettingTermRepository.getTerm(termId)
}