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

    val modifyTimeString: String
        get() {
            val calendar = Calendar.getInstance()
            calendar.timeZone = timeZone
            calendar.timeInMillis = modifyTime
            return dateFormat.format(calendar.time)
        }
    val isExpired: Boolean
        get() = System.currentTimeMillis() - modifyTime > SCRAP_EXPIRE_MILLI

    override fun toString(): String {
        return "id=$id\nname=$name\nurl=$url\nmodifyTime=$modifyTime\ndescription=$description"
    }

    companion object {
        private val timeZone = TimeZone.getTimeZone("Asia/Seoul")
        private val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분", Locale.KOREA)
        private const val SCRAP_EXPIRE_MILLI = 24 * 60 * 60 * 1000 // 1 day
    }

}