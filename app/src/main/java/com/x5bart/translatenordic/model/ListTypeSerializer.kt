package com.x5bart.translatenordic.model

import com.activeandroid.serializer.TypeSerializer
import com.x5bart.translatenordic.utils.Utils
import java.io.Serializable

class ListTypeSerializer : TypeSerializer() {
    override fun getDeserializedType(): Class<*> {
        return List::class.java
    }

    override fun getSerializedType(): Class<*> {
        return ByteArray::class.java
    }

    override fun serialize(o: Any): Any {
        return Utils.serialize(o as Serializable)
    }

    override fun deserialize(o: Any): Any {
        return Utils.deserialize(o as ByteArray)
    }
}
