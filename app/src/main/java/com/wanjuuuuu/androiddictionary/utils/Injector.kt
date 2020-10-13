package com.wanjuuuuu.androiddictionary.utils

import android.content.Context
import androidx.fragment.app.Fragment
import com.wanjuuuuu.androiddictionary.api.AndroidReferenceService
import com.wanjuuuuu.androiddictionary.data.AppDatabase
import com.wanjuuuuu.androiddictionary.data.GettingTermRepository
import com.wanjuuuuu.androiddictionary.data.UpdatingTermRepository
import com.wanjuuuuu.androiddictionary.viewmodels.TermDetailViewModelFactory
import com.wanjuuuuu.androiddictionary.viewmodels.TermListViewModelFactory

object Injector {

    fun provideTermListViewModelFactory(fragment: Fragment): TermListViewModelFactory {
        return TermListViewModelFactory(
            getGettingTermRepository(fragment.requireContext()),
            fragment
        )
    }

    fun provideTermDetailViewModelFactory(
        context: Context,
        termId: Long
    ): TermDetailViewModelFactory {
        return TermDetailViewModelFactory(getGettingTermRepository(context), termId)
    }

    fun getUpdatingTermRepository(context: Context): UpdatingTermRepository {
        return UpdatingTermRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).termDao(),
            AndroidReferenceService.create()
        )
    }

    private fun getGettingTermRepository(context: Context): GettingTermRepository {
        return GettingTermRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).termDao()
        )
    }
}