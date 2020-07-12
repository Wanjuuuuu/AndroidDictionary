package com.wanjuuuuu.androiddictionary.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.wanjuuuuu.androiddictionary.utils.TERM_DATA_FILENAME
import kotlinx.coroutines.*
import java.lang.Exception

class TermRepository(private val coroutineScope: CoroutineScope) {

    companion object {
        private const val TAG = "TermRepository"
    }

    fun getTerms(context: Context): List<Term> {
        context.applicationContext.assets.open(TERM_DATA_FILENAME).use { inputStream ->
            try {
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val type = object : TypeToken<List<Term>>() {}.type
                    val terms: List<Term> = Gson().fromJson(jsonReader, type)
                    return terms
                }
            } catch (e: Exception) {
                Log.e(TAG, "error occurred : $e")
            }
            return listOf()
        }
    }

    fun getTerm(term: Term): Term {
        coroutineScope.launch(Dispatchers.IO) {
            val description = TermScrapper().getDescription(term.url)
            term.description = description
            term.modifyTime = System.currentTimeMillis()
            Log.d(TAG, "$term, ${term.modifyTime} ${term.description}")
        }
        return term
    }
}