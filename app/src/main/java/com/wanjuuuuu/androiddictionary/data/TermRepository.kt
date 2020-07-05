package com.wanjuuuuu.androiddictionary.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.wanjuuuuu.androiddictionary.utils.ANDROID_REFERENCE_BASE_URL
import com.wanjuuuuu.androiddictionary.utils.TERM_DATA_FILENAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.lang.Exception

class TermRepository(private val context: Context) {

    companion object {
        private const val TAG = "TermRepository"
        private const val SELECTOR =
            "#jd-content > p, #jd-content > h3, #jd-content > ol, #jd-content > ul"
    }

    fun getTerms(): List<Term> {
        context.applicationContext.assets.open(TERM_DATA_FILENAME).use { inputStream ->
            try {
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val type = object : TypeToken<List<Term>>() {}.type
                    val terms: List<Term> = Gson().fromJson(jsonReader, type)
                    Log.d(TAG, "$terms")
                    return terms
                }
            } catch (e: Exception) {
                Log.e(TAG, "error occurred : $e")
            }
            return listOf()
        }
    }

    suspend fun getTerm(term: Term): Term {
        val description = getDescription(term.url)
        term.description = description
        term.modifyTime = System.currentTimeMillis()
        return term
    }

    private suspend fun getDescription(url: String): String = withContext(Dispatchers.IO) {
        var description = ""
        try {
            val document = Jsoup.connect("$ANDROID_REFERENCE_BASE_URL$url").get()
            val elements = document.body().select(SELECTOR)
            description = parseDescription(elements)
        } catch (e: Exception) {
            Log.e(TAG, "cannot parse : $e")
        }
        description
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
            element.`is`("ol") -> parseListElement(stringBuilder, element, true)
            element.`is`("ul") -> parseListElement(stringBuilder, element, false)
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