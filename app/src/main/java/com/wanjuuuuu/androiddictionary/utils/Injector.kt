package com.wanjuuuuu.androiddictionary.utils

import android.content.Context
import com.wanjuuuuu.androiddictionary.data.TermRepository
import com.wanjuuuuu.androiddictionary.viewmodels.TermDetailViewModelFactory
import com.wanjuuuuu.androiddictionary.viewmodels.TermListViewModelFactory

object Injector {

    fun provideTermListViewModelFactory(context: Context): TermListViewModelFactory {
        return TermListViewModelFactory(TermRepository(context))
    }

    fun provideTermDetailViewModelFactory(
        context: Context,
        termId: Long
    ): TermDetailViewModelFactory {
        return TermDetailViewModelFactory(TermRepository(context), termId)
    }
}