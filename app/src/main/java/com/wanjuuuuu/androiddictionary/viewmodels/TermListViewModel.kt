package com.wanjuuuuu.androiddictionary.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wanjuuuuu.androiddictionary.data.TermRepository

class TermListViewModel(context: Context) : ViewModel() {
    val terms = TermRepository(context, viewModelScope).getTerms()
}