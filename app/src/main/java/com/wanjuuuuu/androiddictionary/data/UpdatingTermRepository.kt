package com.wanjuuuuu.androiddictionary.data

import com.wanjuuuuu.androiddictionary.api.AndroidReferenceService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdatingTermRepository private constructor(
    private val termDao: TermDao,
    private val androidReferenceService: AndroidReferenceService,
    private val dispatcher: CoroutineDispatcher
) {

    companion object {
        @Volatile
        private var instance: UpdatingTermRepository? = null

        @JvmStatic
        fun getInstance(
            termDao: TermDao,
            androidReferenceService: AndroidReferenceService,
            dispatcher: CoroutineDispatcher = Dispatchers.Default
        ): UpdatingTermRepository {
            return instance ?: synchronized(this) {
                instance ?: UpdatingTermRepository(
                    termDao,
                    androidReferenceService,
                    dispatcher
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
        withContext(dispatcher) {
            termDao.updateTermBookmarked(termId, bookmarked)
        }
    }
}