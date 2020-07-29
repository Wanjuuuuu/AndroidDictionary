package com.wanjuuuuu.androiddictionary.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TermDao {

    @get:Query("select * from terms order by id")
    val allTerms: LiveData<List<Term>>

    @get:Query("select * from terms where bookmarked = 1 order by id")
    val bookmarkedTerms: LiveData<List<Term>>

    @Query("select * from terms where id = :id")
    fun getTerm(id: Long): LiveData<Term>

    @Query("select * from terms where id = :id")
    suspend fun getNaiveTerm(id: Long): Term

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTerms(terms: List<Term>)

    @Query("update terms set description = :description, scrapedTime = :scrapedTime where id = :id")
    suspend fun updateTerm(id: Long, description: String, scrapedTime: Long)

    @Query("update terms set bookmarked = :bookmarked where id = :id")
    suspend fun updateTermBookmarked(id: Long, bookmarked: Boolean)

    @Delete
    fun deleteTerm(term: Term)

    @Delete
    fun deleteTerms(term: Term)
}