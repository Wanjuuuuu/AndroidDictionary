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

    fun getTerm(termId: Long): LiveData<Term> {
        val database = AppDatabase.getInstance(context, coroutineScope)

        coroutineScope.launch(Dispatchers.IO) {
            val term = database.termDao().getNaiveTerm(termId)
            term.description = TermScrapper().getDescription(term.url)
            term.modifyTime = System.currentTimeMillis()
            Log.d(TAG, term.toString())

            database.termDao().updateTerm(term)
        }
        return database.termDao().getTerm(termId)
    }
}