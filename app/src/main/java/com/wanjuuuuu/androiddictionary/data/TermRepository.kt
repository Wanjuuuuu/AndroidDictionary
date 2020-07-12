package com.wanjuuuuu.androiddictionary.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

class TermRepository(private val context: Context, private val coroutineScope: CoroutineScope) {

    companion object {
        private const val TAG = "TermRepository"
    }

    fun getTerms(): LiveData<List<Term>> {
        return AppDatabase.getInstance(context, coroutineScope).termDao().getTerms()
    }

    fun getTerm(term: Term): Term {
        coroutineScope.launch(Dispatchers.IO) {
            val description = TermScrapper().getDescription(term.url)
            term.description = description
            term.modifyTime = System.currentTimeMillis()
            Log.d(TAG, "$term, ${term.modifyTime} ${term.description}")
        }
        return term
    }
}