package com.x5bart.translatenordic.net

import com.x5bart.translatenordic.net.retrofit.ListItemDeserializer
import com.x5bart.translatenordic.net.retrofit.ListItemResponse
import com.x5bart.translatenordic.net.retrofit.SingleItemDeserializer
import com.x5bart.translatenordic.net.retrofit.SingleItemResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

enum class Method constructor(private val itemType: ItemType) {
    GET_LANGS(ItemType.LIST) {
        override fun request(serverAPI: ServerAPI?, params: HashMap<String, String>): Call<ListItemResponse> {
            return serverAPI!!.getLangs(
                params["key"]!!,
                params["ui"]!!
            )
        }
    },
    DETECT(ItemType.SINGLE) {
        override fun request(serverAPI: ServerAPI?, params: HashMap<String, String>): Call<SingleItemResponse> {
            return serverAPI!!.detect(
                params["key"]!!,
                params["text"]!!
            )
        }
    },
    TRANSLATE(ItemType.SINGLE) {
        override fun request(serverAPI: ServerAPI?, params: HashMap<String, String>): Call<SingleItemResponse> {
            return serverAPI!!.translate(
                params["key"]!!,
                params["text"]!!,
                params["lang"]!!
            )
        }
    };

    abstract fun request(serverAPI: ServerAPI?, params: HashMap<String, String>): Call<>

    fun sendRequest(params: HashMap<String, String>, listener: API.OnRequestComplete) {
        var restClient: RestClient? = null
        var serverAPI: ServerAPI? = null
        when (itemType) {
            Method.ItemType.SINGLE -> {
                restClient = RestClient(SingleItemDeserializer())
                serverAPI = RestClient.getServerAPI(restClient)
                val singleCall = request(serverAPI, params)
                singleCall.enqueue(object : Callback<SingleItemResponse> {
                    override fun onResponse(
                        call: Call<SingleItemResponse>,
                        response: Response<SingleItemResponse>
                    ) {
                        if (response.isSuccessful) {
                            listener.onSuccess(response.body()!!)
                        } else {
                            try {
                                val json = JSONObject(response.errorBody()!!.string())
                                listener.onError(
                                    ResponseError(
                                        json.getInt("code"),
                                        json.getString("message")
                                    )
                                )
                            } catch (e: Exception) {
                                listener.onError(ResponseError(response.code(), response.message()))
                            }

                        }
                    }

                    override fun onFailure(call: Call<SingleItemResponse>, t: Throwable) {
                        listener.onError(ResponseError())
                    }
                })
            }
            Method.ItemType.LIST -> {
                restClient = RestClient(ListItemDeserializer())
                serverAPI = RestClient.getServerAPI(restClient)
                val listCall = request(serverAPI, params)
                listCall.enqueue(object : Callback<ListItemResponse> {
                   override fun onResponse(
                        call: Call<ListItemResponse>,
                        response: Response<ListItemResponse>
                    ) {
                        if (response.isSuccessful) {
                            listener.onSuccess(response.body()!!)
                        } else {
                            listener.onError(ResponseError(response.code(), response.message()))
                        }
                    }

                    override fun onFailure(call: Call<ListItemResponse>, t: Throwable) {
                        listener.onError(ResponseError())
                    }
                })
            }
        }
    }

    enum class ItemType {
        SINGLE,
        LIST
    }
}
