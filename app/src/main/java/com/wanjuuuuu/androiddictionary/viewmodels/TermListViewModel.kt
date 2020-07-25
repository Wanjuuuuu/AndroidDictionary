package com.wanjuuuuu.androiddictionary.viewmodels

import androidx.lifecycle.ViewModel
import com.wanjuuuuu.androiddictionary.data.GettingTermRepository

class TermListViewModel(private val gettingTermRepository: GettingTermRepository) : ViewModel() {
    var terms = gettingTermRepository.getAllTerms()
    var isFiltered = false

    fun filterTermsByBookmark(bookmarked: Boolean) {
        terms = if (bookmarked) gettingTermRepository.getTermsBookmarked()
        else gettingTermRepository.getAllTerms()
    }

    fun reverseFilter(): Boolean {
        isFiltered = !isFiltered
        return isFiltered
    }
}