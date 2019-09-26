package com.x5bart.translatenordic.net.retrofit

import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException

class SingleItemDeserializer : TypeAdapter<SingleItemResponse>() {
    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: SingleItemResponse) {

    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): SingleItemResponse {
        val response = SingleItemResponse()
        `in`.beginObject()
        while (`in`.hasNext()) {
            when (`in`.nextName()) {
                "code" -> response.code = `in`.nextInt()
                "text" -> {
                    `in`.beginArray()
                    val gson = GsonBuilder().create()
                    `in`.hasNext()
                    response.text = gson.fromJson<String>(`in`, String::class.java)
                    `in`.endArray()
                }
                "message" -> response.message = `in`.nextString()
                "lang" -> response.lang = `in`.nextString()
                else -> `in`.skipValue()
            }
        }
        `in`.endObject()

        return response
    }
}
