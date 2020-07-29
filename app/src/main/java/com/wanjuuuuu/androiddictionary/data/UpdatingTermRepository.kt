package com.wanjuuuuu.androiddictionary.data

import com.wanjuuuuu.androiddictionary.utils.TermScraper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield

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

    suspend fun updateTermDescriptionIfExpired(termId: Long) {
        val term = termDao.getNaiveTerm(termId)
        if (term.needRescraping) {
            term.description = withContext(Dispatchers.IO) { TermScraper.getDescription(term.url) }
            term.scrapedTime = System.currentTimeMillis()
            termDao.updateTerm(term)
        }
    }

    suspend fun setTermBookmarked(termId: Long, bookmarked: Boolean) {
        termDao.updateTermBookmarked(termId, bookmarked)
    }
}