package com.wanjuuuuu.androiddictionary.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanjuuuuu.androiddictionary.data.TermRepository

class TermDetailViewModel(context: Context, termId: Long) : ViewModel() {
    val term = TermRepository(context, viewModelScope).getTerm(termId)
}