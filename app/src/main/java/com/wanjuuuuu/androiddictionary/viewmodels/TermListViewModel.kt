package com.wanjuuuuu.androiddictionary.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wanjuuuuu.androiddictionary.data.Term
import com.wanjuuuuu.androiddictionary.data.TermRepository

class TermListViewModel(private val context: Context) : ViewModel() {
    val terms: LiveData<List<Term>> = TermRepository(context).getTerms()
}