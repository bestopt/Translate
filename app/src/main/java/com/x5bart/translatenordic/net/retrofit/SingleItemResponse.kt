package com.x5bart.translatenordic.net.retrofit

public class SingleItemResponse : ResponseItem {
    companion object {
        var code: Int = 0
        var lang: String? = null
        var text: String? = null
        var message: String? = null
    }
}