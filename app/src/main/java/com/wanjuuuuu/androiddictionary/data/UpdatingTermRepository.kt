package com.wanjuuuuu.androiddictionary.data

import com.wanjuuuuu.androiddictionary.utils.TermScrapper

class UpdatingTermRepository private constructor(private val termDao: TermDao) {

    companion object {
        @Volatile
        private var instance: UpdatingTermRepository? = null

        @JvmStatic
        fun getInstance(termDao: TermDao): UpdatingTermRepository {
            return instance ?: synchronized(this) {
                instance ?: UpdatingTermRepository(termDao).also { instance = it }
            }
        }
    }

    fun updateTermDescriptionIfExpired(termId: Long) {
        val term = termDao.getNaiveTerm(termId)
        if (term.isExpired) {
            term.description = TermScrapper.getDescription(term.url)
            term.modifyTime = System.currentTimeMillis()
            termDao.updateTerm(term)
        }
    }

    fun setTermBookmarked(termId: Long, bookmarked: Boolean) {
        termDao.updateTermBookmarked(termId, bookmarked)
    }
}