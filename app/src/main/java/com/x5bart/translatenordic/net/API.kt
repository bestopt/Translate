package com.x5bart.translatenordic.net

import com.x5bart.translatenordic.net.retrofit.ResponseItem
import java.util.HashMap

object API {
    interface OnRequestComplete {
        fun onSuccess(response: ResponseItem)

        fun onError(error: ResponseError)
    }

    fun sendRequest(
        method: Method,
        params: HashMap<String, String>, @NonNull listener: OnRequestComplete
    ) {

        if (!Utils.hasConnection()) {
            listener.onError(ResponseError(ServerError.NO_CONNECTION))
            return
        }
        method.sendRequest(params, object : OnRequestComplete {
            override fun onSuccess(response: ResponseItem) {
                listener.onSuccess(response)
            }

            override fun onError(error: ResponseError) {
                listener.onError(error)
            }
        })
    }
}