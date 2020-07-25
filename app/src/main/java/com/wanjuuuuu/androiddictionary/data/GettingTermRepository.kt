package com.wanjuuuuu.androiddictionary.data

import androidx.lifecycle.LiveData

class GettingTermRepository private constructor(private val termDao: TermDao) {

    companion object {
        @Volatile
        private var instance: GettingTermRepository? = null

        @JvmStatic
        fun getInstance(termDao: TermDao): GettingTermRepository {
            return instance ?: synchronized(this) {
                instance ?: GettingTermRepository(termDao).also { instance = it }
            }
        }
    }

    fun getAllTerms(): LiveData<List<Term>> {
        return termDao.getTerms()
    }

    fun getTermsBookmarked(): LiveData<List<Term>> {
        return termDao.getTermsBoorkmarked()
    }

    fun getTerm(termId: Long): LiveData<Term> {
        return termDao.getTerm(termId)
    }
}