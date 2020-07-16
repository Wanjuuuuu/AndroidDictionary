package com.wanjuuuuu.androiddictionary.data

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

class TermRepository(private val context: Context) {

    fun getTerms(coroutineScope: CoroutineScope): LiveData<List<Term>> {
        return AppDatabase.getInstance(context, coroutineScope).termDao().getTerms()
    }

    fun getTerm(termId: Long, coroutineScope: CoroutineScope): LiveData<Term> {
        val database = AppDatabase.getInstance(context, coroutineScope)

        coroutineScope.launch(Dispatchers.Default) {
            val term = database.termDao().getNaiveTerm(termId)
            if (term.isExpired) {
                term.description = TermScrapper().getDescription(term.url)
                term.modifyTime = System.currentTimeMillis()
                database.termDao().updateTerm(term)
            }
        }
        return database.termDao().getTerm(termId)
    }
}