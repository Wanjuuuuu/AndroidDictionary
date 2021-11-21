package com.wanjuuuuu.androiddictionary.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
abstract class TermDao {

    val allTerms: Flow<List<TermListItem>>
        get() = allTerms().distinctUntilChanged()

    val bookmarkedTerms: Flow<List<TermListItem>>
        get() = bookmarkedTerms().distinctUntilChanged()

    @Query("select id, name, category, bookmarked from terms order by id")
    protected abstract fun allTerms(): Flow<List<TermListItem>>

    @Query("select id, name, category, bookmarked from terms where bookmarked = 1 order by id")
    protected abstract fun bookmarkedTerms(): Flow<List<TermListItem>>

    @Query("select * from terms where id = :id")
    abstract fun getTerm(id: Long): LiveData<Term>

    @Query("select * from terms where id = :id")
    abstract suspend fun getNaiveTerm(id: Long): Term

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertTerms(terms: List<Term>)

    @Query("update terms set description = :description, scrapedTime = :scrapedTime where id = :id")
    abstract suspend fun updateTerm(id: Long, description: String, scrapedTime: Long)

    @Query("update terms set bookmarked = :bookmarked where id = :id")
    abstract suspend fun updateTermBookmarked(id: Long, bookmarked: Boolean)

    @Delete
    abstract fun deleteTerm(term: Term)

    @Delete
    abstract fun deleteTerms(term: Term)
}