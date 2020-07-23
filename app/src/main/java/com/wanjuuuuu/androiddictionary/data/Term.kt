package com.wanjuuuuu.androiddictionary.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "terms")
data class Term(
    val name: String,
    val url: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var description: String? = null
    var modifyTime: Long = 0
    var bookmarked: Boolean = false

    val isExpired: Boolean
        get() = System.currentTimeMillis() - modifyTime > SCRAP_EXPIRE_MILLI

    override fun toString(): String {
        return "id=$id\nname=$name\nurl=$url\nmodifyTime=$modifyTime\ndescription=$description"
    }

    companion object {
        private const val SCRAP_EXPIRE_MILLI = 24 * 60 * 60 * 1000 // 1 day
    }
}