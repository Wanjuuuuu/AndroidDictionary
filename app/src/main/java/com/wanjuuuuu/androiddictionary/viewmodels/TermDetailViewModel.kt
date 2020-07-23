package com.wanjuuuuu.androiddictionary.viewmodels

import androidx.lifecycle.ViewModel
import com.wanjuuuuu.androiddictionary.data.TermRepository

class TermDetailViewModel(private val termRepository: TermRepository, private val termId: Long) :
    ViewModel() {
    val term = termRepository.getTerm(termId)

    fun updateTermDescription() {
        termRepository.updateTermDescriptionIfExpired(termId)
    }
}