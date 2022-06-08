package com.wanjuuuuu.androiddictionary.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "terms")
data class Term(
    val name: String,
    val category: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var description: String? = null
    var scrapedTime: Long = 0
    var bookmarked: Boolean = false

    @get:Ignore
    val needRescraping: Boolean
        get() = System.currentTimeMillis() - scrapedTime > SCRAP_EXPIRE_MILLI

    @get:Ignore
    val url: String
        get() = "$category/$name"

    override fun toString(): String {
        return "id=$id\nurl=$url\nscrapedTime=$scrapedTime\nbookmarked=$bookmarked"
    }

    companion object {
        private const val SCRAP_EXPIRE_MILLI = 24 * 60 * 60 * 1000 // 1 day
    }
}