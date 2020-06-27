package com.wanjuuuuu.androiddictionary.data

data class Term(val name: String, val url: String) {
    var description: String = ""
    var date: Long = System.currentTimeMillis();
}