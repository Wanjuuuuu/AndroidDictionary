package com.wanjuuuuu.androiddictionary.viewmodels

import androidx.lifecycle.ViewModel
import com.wanjuuuuu.androiddictionary.data.Term

class TermListViewModel() : ViewModel() {
    // TODO : change into LiveData
    val terms: List<Term> = listOf(Term("name", "url/url"))
}