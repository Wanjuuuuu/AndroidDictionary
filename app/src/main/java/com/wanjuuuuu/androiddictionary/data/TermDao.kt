package com.wanjuuuuu.androiddictionary.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TermDao {

    @Query("select * from terms order by id")
    fun getTerms(): LiveData<List<Term>>

    @Query("select * from terms where id = :id")
    fun getTerm(id: Long): LiveData<Term>

    @Query("select * from terms where id = :id")
    fun getNaiveTerm(id: Long): Term

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTerms(terms: List<Term>)

    @Update
    fun updateTerm(term: Term)

    @Delete
    fun deleteTerm(term: Term)

    @Delete
    fun deleteTerms(term: Term)
}