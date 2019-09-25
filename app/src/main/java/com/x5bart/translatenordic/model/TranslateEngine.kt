package com.x5bart.translatenordic.model

import com.x5bart.translatenordic.BuildConfig

public class TranslateEngine {
    companion object {
        public fun currentApiKey(): String {
            return BuildConfig.API_KEY
        }
    }
}