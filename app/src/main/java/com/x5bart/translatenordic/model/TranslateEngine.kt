package com.x5bart.translatenordic.model

import com.x5bart.translatenordic.BuildConfig

class TranslateEngine {
    companion object {
        fun getCurrentApiKey(): String {
            return BuildConfig.API_KEY
        }
    }
}