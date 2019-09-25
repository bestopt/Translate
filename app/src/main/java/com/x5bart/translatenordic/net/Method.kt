package com.x5bart.translatenordic.net

import java.util.HashMap

enum class Method constructor(val itemType: ItemType) {
    GET_LANGS(ItemType.LIST) {
        override fun request(serverAPI: ServerAPI?, params: HashMap<String, String>): Call {
            return serverAPI!!.getLangs(
                params["key"],
                params["ui"]
            )
        }
    },
    DETECT(ItemType.SINGLE) {
        override fun request(serverAPI: ServerAPI?, params: HashMap<String, String>): Call {
            return serverAPI!!.detect(
                params["key"],
                params["text"]
            )
        }
    },
    TRANSLATE(ItemType.SINGLE) {
        override fun request(serverAPI: ServerAPI?, params: HashMap<String, String>): Call {
            return serverAPI!!.translate(
                params["key"],
                params["text"],
                params["lang"]
            )
        }
    };

    abstract fun request(serverAPI: ServerAPI?, params: HashMap<String, String>): Call

    fun sendRequest(params: HashMap<String, String>, listener: API.OnRequestComplete) {
        var restClient: RestClient? = null
        var serverAPI: ServerAPI? = null
        when (itemType) {
            Method.ItemType.SINGLE -> {
                restClient = RestClient(SingleItemDeserializer())
                serverAPI = restClient!!.getServerAPI()
                val singleCall = request(serverAPI, params)
                singleCall.enqueue(object : Callback<SingleItemResponse>() {
                    fun onResponse(
                        call: Call<SingleItemResponse>,
                        response: Response<SingleItemResponse>
                    ) {
                        if (response.isSuccessful()) {
                            listener.onSuccess(response.body())
                        } else {
                            try {
                                val json = JSONObject(response.errorBody().string())
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

                    fun onFailure(call: Call<SingleItemResponse>, t: Throwable) {
                        listener.onError(ResponseError())
                    }
                })
            }
            Method.ItemType.LIST -> {
                restClient = RestClient(ListItemDeserializer())
                serverAPI = restClient!!.getServerAPI()
                val listCall = request(serverAPI, params)
                listCall.enqueue(object : Callback<ListItemResponse>() {
                    fun onResponse(
                        call: Call<ListItemResponse>,
                        response: Response<ListItemResponse>
                    ) {
                        if (response.isSuccessful()) {
                            listener.onSuccess(response.body())
                        } else {
                            listener.onError(ResponseError(response.code(), response.message()))
                        }
                    }

                    fun onFailure(call: Call<ListItemResponse>, t: Throwable) {
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
