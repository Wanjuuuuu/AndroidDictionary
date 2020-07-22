package com.wanjuuuuu.androiddictionary.utils

import android.content.Context
import com.wanjuuuuu.androiddictionary.data.AppDatabase
import com.wanjuuuuu.androiddictionary.data.TermRepository
import com.wanjuuuuu.androiddictionary.viewmodels.TermDetailViewModelFactory
import com.wanjuuuuu.androiddictionary.viewmodels.TermListViewModelFactory

object Injector {

    fun provideTermListViewModelFactory(context: Context): TermListViewModelFactory {
        return TermListViewModelFactory(getTermRepository(context))
    }

    fun provideTermDetailViewModelFactory(
        context: Context,
        termId: Long
    ): TermDetailViewModelFactory {
        return TermDetailViewModelFactory(getTermRepository(context), termId)
    }

    private fun getTermRepository(context: Context): TermRepository {
        return TermRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).termDao()
        )
    }
}