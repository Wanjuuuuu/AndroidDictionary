package com.wanjuuuuu.androiddictionary.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.wanjuuuuu.androiddictionary.utils.DATABASE_NAME

@Database(entities = [Term::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun termDao(): TermDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                with(database) {
                    execSQL("CREATE TABLE new_terms (name TEXT NOT NULL, category TEXT NOT NULL, id Integer NOT NULL, description TEXT, scrapedTime Integer NOT NULL, bookmarked Integer NOT NULL, PRIMARY KEY(`id`)) ")
                    execSQL("INSERT INTO new_terms (name, category, id, description, scrapedTime, bookmarked) SELECT name, substr(url, 0, length(url) - length(name)), id, description, scrapedTime, bookmarked FROM terms")
                    execSQL("DROP TABLE terms")
                    execSQL("ALTER TABLE new_terms RENAME TO terms")
                }
            }
        }

        @JvmStatic
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addMigrations(MIGRATION_1_2)
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