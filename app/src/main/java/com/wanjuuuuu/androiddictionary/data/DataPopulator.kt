package com.wanjuuuuu.androiddictionary.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.wanjuuuuu.androiddictionary.utils.TERM_DATA_FILENAME
import java.lang.Exception

class DataPopulator(private val context: Context) {

    companion object {
        const val TAG = "DataPopulator"
    }

    fun prepopulate(): List<Term> {
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
}