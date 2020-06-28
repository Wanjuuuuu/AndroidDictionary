package com.wanjuuuuu.androiddictionary.data

import java.text.SimpleDateFormat
import java.util.*

data class Term(val name: String, val url: String) {

    companion object {
        val timeZone = TimeZone.getTimeZone("Asia/Seoul")
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분", Locale.KOREA)
    }

    var description: String = ""
    var modifyTime: Long = System.currentTimeMillis()
    val modifyTimeString: String
        get() {
            val calendar = Calendar.getInstance()
            calendar.timeZone = timeZone
            calendar.timeInMillis = modifyTime
            return dateFormat.format(calendar.time)
        }
}