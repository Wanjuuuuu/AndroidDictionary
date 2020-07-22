package com.wanjuuuuu.androiddictionary.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

class TermRepository private constructor(private val termDao: TermDao) {

    companion object {
        @Volatile
        private var instance: TermRepository? = null

        @JvmStatic
        fun getInstance(termDao: TermDao): TermRepository {
            return instance ?: synchronized(this) {
                instance ?: TermRepository(termDao).also { instance = it }
            }
        }
    }

    fun getTerms(): LiveData<List<Term>> {
        return termDao.getTerms()
    }

    fun getTerm(termId: Long, coroutineScope: CoroutineScope): LiveData<Term> {
        coroutineScope.launch(Dispatchers.Default) {
            val term = termDao.getNaiveTerm(termId)
            if (term.isExpired) {
                term.description = TermScrapper().getDescription(term.url)
                term.modifyTime = System.currentTimeMillis()
                termDao.updateTerm(term)
            }
        }
        return termDao.getTerm(termId)
    }
}