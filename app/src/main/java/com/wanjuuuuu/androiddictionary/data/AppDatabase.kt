package com.wanjuuuuu.androiddictionary.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.wanjuuuuu.androiddictionary.utils.DATABASE_NAME

@Database(entities = [Term::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun termDao(): TermDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        val request = OneTimeWorkRequestBuilder<DataPopulator>().build()
                        WorkManager.getInstance(context).enqueue(request)
                    }
                }).build()
        }
    }
}