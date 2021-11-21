package com.wanjuuuuu.androiddictionary.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

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

    fun getAllTerms(): Flow<List<TermListItem>> {
        return termDao.allTerms
    }

    fun getBookmarkedTerms(): Flow<List<TermListItem>> {
        return termDao.bookmarkedTerms
    }

    fun getCategorizedTerms(terms: Flow<List<TermListItem>>): Flow<Map<String, List<TermListItem>>> {
        return terms.map { categorize(it) }.flowOn(dispatcher)
    }

    private fun categorize(terms: List<TermListItem>): Map<String, List<TermListItem>> {
        return terms.groupBy { it.category }
    }

    fun getTerm(termId: Long): LiveData<Term> {
        return termDao.getTerm(termId)
    }
}