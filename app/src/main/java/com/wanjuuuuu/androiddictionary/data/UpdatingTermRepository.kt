package com.wanjuuuuu.androiddictionary.data

import com.wanjuuuuu.androiddictionary.utils.TermScraper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    suspend fun refreshTermDescription(term: Term) {
        val description = withContext(Dispatchers.IO) { TermScraper.getDescription(term.url) }
        val scrapedTime = System.currentTimeMillis()
        termDao.updateTerm(term.id, description, scrapedTime)
    }

    suspend fun setTermBookmarked(termId: Long, bookmarked: Boolean) {
        termDao.updateTermBookmarked(termId, bookmarked)
    }
}