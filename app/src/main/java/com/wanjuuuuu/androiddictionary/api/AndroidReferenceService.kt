package com.wanjuuuuu.androiddictionary.api

import com.wanjuuuuu.androiddictionary.utils.ANDROID_REFERENCE_BASE_URL
import com.wanjuuuuu.androiddictionary.utils.TermScraper
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import java.lang.reflect.Type

interface AndroidReferenceService {

    @GET("{path}")
    suspend fun getReferencePage(@Path("path") path: String): Response<String>

    companion object {

        fun create(): AndroidReferenceService {
            return Retrofit.Builder().baseUrl(ANDROID_REFERENCE_BASE_URL)
                .addConverterFactory(ReferenceConverter.factory).build()
                .create(AndroidReferenceService::class.java)
        }
    }
}

class ReferenceConverter : Converter<ResponseBody, String> {

    companion object {
        val factory = object : Converter.Factory() {
            override fun responseBodyConverter(
                type: Type,
                annotations: Array<Annotation>,
                retrofit: Retrofit
            ): Converter<ResponseBody, *>? {
                if (type == String::class.java) return ReferenceConverter()
                return null
            }
        }
    }

    override fun convert(value: ResponseBody): String? {
        return TermScraper.getDescription(value.string())
    }
}