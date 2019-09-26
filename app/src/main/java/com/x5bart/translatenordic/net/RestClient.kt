package com.x5bart.translatenordic.net

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.x5bart.translatenordic.BuildConfig
import com.x5bart.translatenordic.net.retrofit.ListItemResponse
import com.x5bart.translatenordic.net.retrofit.SingleItemDeserializer
import com.x5bart.translatenordic.net.retrofit.SingleItemResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestClient(typeAdapter: TypeAdapter<*>) {
     val serverAPI: ServerAPI

    init {
        val gson: Gson
        if (typeAdapter is SingleItemDeserializer) {
            gson = GsonBuilder().registerTypeAdapter(SingleItemResponse::class.java, typeAdapter)
                .setDateFormat("yyyy-MM-dd")
                .create()
        } else {
            gson = GsonBuilder().registerTypeAdapter(ListItemResponse::class.java, typeAdapter)
                .setDateFormat("yyyy-MM-dd")
                .create()
        }

        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
            clientBuilder.addInterceptor(interceptor)
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        serverAPI = retrofit.create(ServerAPI::class.java)
    }

    companion object {
        private const val BASE_URL = BuildConfig.HOST
        fun getServerAPI(restClient: RestClient): ServerAPI {
            return restClient.serverAPI
        }
    }

}
