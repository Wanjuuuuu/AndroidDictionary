package com.wanjuuuuu.androiddictionary.utils

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.lang.Exception

object TermScraper {

    private const val TAG = "TermScraper"
    private const val SELECTOR =
        "#jd-content > p, #jd-content > h3, #jd-content > ol, #jd-content > ul"

    fun getDescription(url: String): String {
        var description = ""
        try {
            val document = Jsoup.connect("$ANDROID_REFERENCE_BASE_URL$url").get()
            val elements = document.body().select(SELECTOR)
            description = parseDescription(elements)
        } catch (e: Exception) {
            Log.e(TAG, "cannot parse : $e")
        }
        return description
    }

    private fun parseDescription(elements: Elements): String {
        val stringBuilder = StringBuilder()
        for ((index, element) in elements.withIndex()) {
            val isLastLine = (index == elements.lastIndex)
            val text = parseElement(element, isLastLine)
            appendString(stringBuilder, text, isLastLine)
            removeNewLineIfBlankAndLastLine(stringBuilder, text, isLastLine)
        }
        return stringBuilder.toString()
    }

    private fun parseElement(element: Element, isLastLine: Boolean): String {
        return when {
            element.`is`("p") -> parsePlainElement(element, isLastLine)
            element.`is`("ol") -> parseListElement(element, true, isLastLine)
            element.`is`("ul") -> parseListElement(element, false, isLastLine)
            else -> parsePlainElement(element, isLastLine)
        }
    }

    private fun parsePlainElement(element: Element, isLastLine: Boolean): String {
        val stringBuilder = StringBuilder()
        appendString(stringBuilder, element.text(), isLastLine)
        return stringBuilder.toString()
    }

    private fun parseListElement(
        listElement: Element,
        isOrdered: Boolean,
        isLastLine: Boolean
    ): String {
        val stringBuilder = StringBuilder()
        val elements = listElement.select("li")
        for ((index, element) in elements.withIndex()) {
            stringBuilder.append(if (isOrdered) "\t${index + 1}. " else "\t- ")
            appendString(stringBuilder, element.text(), isLastLine)
        }
        return stringBuilder.toString()
    }

    private fun appendString(stringBuilder: StringBuilder, text: String, isLastLine: Boolean) {
        if (text.isBlank()) return
        if (isLastLine) stringBuilder.append(text)
        else stringBuilder.appendln(text)
    }

    private fun removeNewLineIfBlankAndLastLine(
        stringBuilder: StringBuilder,
        text: String,
        isLastLine: Boolean
    ) {
        if (text.isBlank() && isLastLine && stringBuilder.length >= 2) {
            stringBuilder.setLength(stringBuilder.length - 2)
        }
    }
}