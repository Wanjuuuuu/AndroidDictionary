package com.wanjuuuuu.androiddictionary.data

import androidx.lifecycle.LiveData
import com.wanjuuuuu.androiddictionary.utils.TermScrapper
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

    fun getTerm(termId: Long): LiveData<Term> {
        return termDao.getTerm(termId)
    }

    fun updateTermDescriptionIfExpired(termId: Long) {
        val term = termDao.getNaiveTerm(termId)
        if (term.isExpired) {
            term.description = TermScrapper.getDescription(term.url)
            term.modifyTime = System.currentTimeMillis()
            termDao.updateTerm(term)
        }
    }
}