package com.wanjuuuuu.androiddictionary.utils

val Any.TAG: String
    get() {
        val tag = "[AndDic] ${this.javaClass.simpleName}"
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }