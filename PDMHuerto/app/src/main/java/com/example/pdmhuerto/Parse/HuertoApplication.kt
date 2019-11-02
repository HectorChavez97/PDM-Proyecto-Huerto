package com.example.pdmhuerto.Parse

import android.app.Application
import com.parse.Parse

class HuertoApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Parse.initialize(
            Parse.Configuration.Builder(this).applicationId("8D2ZDKb1fgnP1baph5OCWZ8JZ4xgOH1tZbe5VyDr")
                .clientKey("SXSz0s1GTzIMJbQSLuTgCpL81MMGEqAx6XMbPN4c")
                .server("https://parseapi.back4app.com/")
                .build()
        )

    }
}