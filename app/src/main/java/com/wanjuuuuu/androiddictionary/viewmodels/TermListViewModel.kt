package com.wanjuuuuu.androiddictionary.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.wanjuuuuu.androiddictionary.data.Term
import com.wanjuuuuu.androiddictionary.data.TermRepository

class TermListViewModel(context: Context) : ViewModel() {
    val terms: LiveData<List<Term>> = liveData {
        val data = TermRepository(context).getTerms()
        emit(data)
    }
}