package com.wanjuuuuu.androiddictionary.data

import com.wanjuuuuu.androiddictionary.api.AndroidReferenceService

class UpdatingTermRepository private constructor(
    private val termDao: TermDao,
    private val androidReferenceService: AndroidReferenceService
) {

    companion object {
        @Volatile
        private var instance: UpdatingTermRepository? = null

        @JvmStatic
        fun getInstance(
            termDao: TermDao,
            androidReferenceService: AndroidReferenceService
        ): UpdatingTermRepository {
            return instance ?: synchronized(this) {
                instance ?: UpdatingTermRepository(
                    termDao,
                    androidReferenceService
                ).also { instance = it }
            }
        }
    }

    suspend fun refreshTermDescription(term: Term) {
        androidReferenceService.getReferencePage(term.url).let {
            termDao.updateTerm(term.id, it, System.currentTimeMillis())
        }
    }

    suspend fun setTermBookmarked(termId: Long, bookmarked: Boolean) {
        termDao.updateTermBookmarked(termId, bookmarked)
    }
}