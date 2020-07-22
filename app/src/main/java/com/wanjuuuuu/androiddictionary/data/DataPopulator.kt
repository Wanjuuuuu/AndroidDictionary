package com.wanjuuuuu.androiddictionary.data

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.wanjuuuuu.androiddictionary.utils.TERM_DATA_FILENAME
import kotlinx.coroutines.coroutineScope
import java.lang.Exception

class DataPopulator(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    companion object {
        const val TAG = "DataPopulator"
    }

    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(TERM_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val type = object : TypeToken<List<Term>>() {}.type
                    val terms: List<Term> = Gson().fromJson(jsonReader, type)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.termDao().insertTerms(terms)

                    Result.success()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "error occurred : $e")
            Result.failure()
        }
    }
}