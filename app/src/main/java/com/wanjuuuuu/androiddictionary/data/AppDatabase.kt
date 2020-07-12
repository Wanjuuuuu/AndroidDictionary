package com.wanjuuuuu.androiddictionary.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.wanjuuuuu.androiddictionary.utils.DATABASE_NAME
import kotlinx.coroutines.*

@Database(entities = [Term::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun termDao(): TermDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        @JvmStatic
        fun getInstance(context: Context, coroutineScope: CoroutineScope): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context, coroutineScope).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context, coroutineScope: CoroutineScope): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        coroutineScope.launch(Dispatchers.IO) {
                            val terms = DataPopulator(context).prepopulate()
                            getInstance(context, coroutineScope).termDao().insertTerms(terms)
                        }
                    }
                }).build()
        }
    }
}