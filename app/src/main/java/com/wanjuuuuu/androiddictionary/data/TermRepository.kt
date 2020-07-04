package com.wanjuuuuu.androiddictionary.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.wanjuuuuu.androiddictionary.utils.TERM_DATA_FILENAME
import java.lang.Exception

class TermRepository(private val context: Context) {

    companion object {
        private const val TAG = "TermRepository"
    }

    fun getTerms(): LiveData<List<Term>> {
        context.applicationContext.assets.open(TERM_DATA_FILENAME).use { inputStream ->
            val termLiveData = MutableLiveData<List<Term>>()
            try {
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val type = object : TypeToken<List<Term>>() {}.type
                    val terms: List<Term> = Gson().fromJson(jsonReader, type)
                    termLiveData.value = terms
                }
            } catch (e: Exception) {
                Log.e(TAG, "error occured : $e")
            }
            return termLiveData
        }
    }
}