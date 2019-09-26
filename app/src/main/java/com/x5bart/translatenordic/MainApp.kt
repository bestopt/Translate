package com.x5bart.translatenordic

import com.activeandroid.ActiveAndroid
import com.activeandroid.Configuration
import com.x5bart.translatenordic.model.ListTypeSerializer

class MainApp : com.activeandroid.app.Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        ActiveAndroid.initialize(
            Configuration.Builder(this)
                .setDatabaseName("translator.db")
                .setDatabaseVersion(1)
                .addTypeSerializer(ListTypeSerializer::class.java)
                .create(), false
        )
    }

    companion object {
        var instance: MainApp? = null
            private set
    }
}
