package com.x5bart.translatenordic.net.retrofit

import com.activeandroid.ActiveAndroid
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.x5bart.translatenordic.model.Lang
import java.io.IOException

class ListItemDeserializer : TypeAdapter<ListItemResponse>() {
    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: ListItemResponse) {

    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): ListItemResponse {
        val response = ListItemResponse()
        `in`.beginObject()
        while (`in`.hasNext()) {
            val currentToken = `in`.nextName()
            if (currentToken == "langs") {
                `in`.beginObject()
                try {
                    ActiveAndroid.beginTransaction()
                    while (`in`.hasNext()) {
                        val name = `in`.nextName()
                        val value = `in`.nextString()
                        Lang(name, value)
                    }
                    ActiveAndroid.setTransactionSuccessful()
                } finally {
                    ActiveAndroid.endTransaction()
                }
                `in`.endObject()
            } else {
                `in`.skipValue()
            }
        }
        `in`.endObject()

        return response
    }
}
