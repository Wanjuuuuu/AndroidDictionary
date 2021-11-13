package com.wanjuuuuu.androiddictionary.data

import androidx.annotation.AnyThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GettingTermRepository private constructor(
    private val termDao: TermDao,
    private val dispatcher: CoroutineDispatcher
) {

    companion object {
        @Volatile
        private var instance: GettingTermRepository? = null

        @JvmStatic
        fun getInstance(
            termDao: TermDao,
            dispatcher: CoroutineDispatcher = Dispatchers.Default
        ): GettingTermRepository {
            return instance ?: synchronized(this) {
                instance ?: GettingTermRepository(termDao, dispatcher).also { instance = it }
            }
        }
    }

    fun getAllTerms(): LiveData<List<Term>> {
        return termDao.allTerms
    }

    fun getBookmarkedTerms(): LiveData<List<Term>> {
        return termDao.bookmarkedTerms
    }

    fun getCategorizedTerms(terms: List<Term>): LiveData<Map<String, List<Term>>> {
        return liveData { emit(terms.categorize()) }
    }

    @AnyThread
    private suspend fun List<Term>.categorize(): Map<String, List<Term>> {
        return withContext(dispatcher) { groupBy { it.category } }
    }

    fun getTerm(termId: Long): LiveData<Term> {
        return termDao.getTerm(termId)
    }
}