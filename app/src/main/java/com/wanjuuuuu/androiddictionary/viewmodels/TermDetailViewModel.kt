package com.wanjuuuuu.androiddictionary.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.wanjuuuuu.androiddictionary.data.Term
import com.wanjuuuuu.androiddictionary.data.TermRepository

class TermDetailViewModel(context: Context, temp: Term) : ViewModel() {
    val term: LiveData<Term> = liveData {
        val data = TermRepository(context, viewModelScope).getTerm(temp)
        emit(data)
    }
}