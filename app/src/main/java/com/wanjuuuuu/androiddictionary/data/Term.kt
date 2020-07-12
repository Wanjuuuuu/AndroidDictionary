package com.wanjuuuuu.androiddictionary.data

import androidx.annotation.Nullable
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

    companion object {
        private val timeZone = TimeZone.getTimeZone("Asia/Seoul")
        private val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분", Locale.KOREA)
    }

    val modifyTimeString: String
        get() {
            val calendar = Calendar.getInstance()
            calendar.timeZone = timeZone
            calendar.timeInMillis = modifyTime
            return dateFormat.format(calendar.time)
        }

    override fun toString(): String {
        return "id=$id\nname=$name\nurl=$url\nmodifyTime=$modifyTime\ndescription=$description"
    }
}