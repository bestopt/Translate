package com.x5bart.translatenordic.utils

import android.content.Context
import android.net.ConnectivityManager
import com.x5bart.translatenordic.MainActivity
import com.x5bart.translatenordic.MainApp
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

object Utils {
    fun serialize(`object`: Serializable): ByteArray {
        try {
            val result: ByteArray
            val bos = ByteArrayOutputStream()
            val out = ObjectOutputStream(bos)
            out.writeObject(`object`)
            result = bos.toByteArray()
            out.close()
            bos.close()
            return result
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    fun <T : Serializable> deserialize(bytes: ByteArray): T {
        try {
            val result: T
            val bis = ByteArrayInputStream(bytes)
            val `in` = ObjectInputStream(bis)
            result = `in`.readObject() as T
            bis.close()
            `in`.close()
            return result
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    fun hasConnection(): Boolean {
        val conMgr =
            MainApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = conMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
}
