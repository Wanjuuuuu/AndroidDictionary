package com.wanjuuuuu.androiddictionary.utils

import android.content.Context
import com.wanjuuuuu.androiddictionary.data.AppDatabase
import com.wanjuuuuu.androiddictionary.data.GettingTermRepository
import com.wanjuuuuu.androiddictionary.data.UpdatingTermRepository
import com.wanjuuuuu.androiddictionary.viewmodels.TermDetailViewModelFactory
import com.wanjuuuuu.androiddictionary.viewmodels.TermListViewModelFactory

object Injector {

    fun provideTermListViewModelFactory(context: Context): TermListViewModelFactory {
        return TermListViewModelFactory(getGettingTermRepository(context))
    }

    fun provideTermDetailViewModelFactory(
        context: Context,
        termId: Long
    ): TermDetailViewModelFactory {
        return TermDetailViewModelFactory(getGettingTermRepository(context), termId)
    }

    fun getUpdatingTermRepository(context: Context): UpdatingTermRepository {
        return UpdatingTermRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).termDao()
        )
    }

    private fun getGettingTermRepository(context: Context): GettingTermRepository {
        return GettingTermRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).termDao()
        )
    }
}