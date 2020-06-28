package com.wanjuuuuu.androiddictionary.viewmodels

import androidx.lifecycle.ViewModel
import com.wanjuuuuu.androiddictionary.data.Term

class TermDetailViewModel : ViewModel() {
    // TODO : change into LiveData
    val term = Term("name", "url/url")
}