package com.wanjuuuuu.androiddictionary.utils

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.lang.Exception

object TermScrapper {

    private const val TAG = "TermScrapper"
    private const val SELECTOR =
        "#jd-content > p, #jd-content > h3, #jd-content > ol, #jd-content > ul"

    fun getDescription(url: String): String {
        var description = ""
        try {
            val document = Jsoup.connect("$ANDROID_REFERENCE_BASE_URL$url").get()
            val elements = document.body().select(SELECTOR)
            description = parseDescription(elements)
            Log.d(TAG, description)
        } catch (e: Exception) {
            Log.e(TAG, "cannot parse : $e")
        }
        return description
    }

    private fun parseDescription(elements: Elements): String {
        val stringBuilder = StringBuilder()
        for ((index, element) in elements.withIndex()) {
            parseElement(stringBuilder, element)
            if (index != elements.lastIndex) {
                stringBuilder.appendln()
            }
        }
        return stringBuilder.toString()
    }

    private fun parseElement(stringBuilder: StringBuilder, element: Element) {
        when {
            element.`is`("p") -> {
                stringBuilder.append(" ")
                stringBuilder.appendln(element.text())
            }
            element.`is`("ol") -> parseListElement(
                stringBuilder,
                element,
                true
            )
            element.`is`("ul") -> parseListElement(
                stringBuilder,
                element,
                false
            )
            else -> stringBuilder.appendln(element.text())
        }
    }

    private fun parseListElement(
        stringBuilder: StringBuilder,
        listElement: Element,
        isOrdered: Boolean
    ) {
        val elements = listElement.select("li")
        for ((index, element) in elements.withIndex()) {
            stringBuilder.append(if (isOrdered) "\t${index + 1}. " else "\t- ")
            stringBuilder.appendln(element.text())
        }
    }
}