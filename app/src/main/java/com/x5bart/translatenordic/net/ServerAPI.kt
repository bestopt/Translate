package com.x5bart.translatenordic.net

import com.x5bart.translatenordic.net.retrofit.ListItemResponse
import com.x5bart.translatenordic.net.retrofit.SingleItemResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface ServerAPI {
    @POST("getLangs")
    abstract fun getLangs(@Query("key") key: String, @Query("ui") ui: String): Call<ListItemResponse>

    @POST("detect")
    abstract fun detect(@Query("key") key: String, @Query("text") text: String): Call<SingleItemResponse>

    @POST("translate")
    abstract fun translate(@Query("key") key: String, @Query("text") text: String, @Query("lang") lang: String): Call<SingleItemResponse>
}

}