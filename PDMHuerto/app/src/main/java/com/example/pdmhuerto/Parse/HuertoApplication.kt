package com.example.pdmhuerto.Parse

import android.app.Application
import com.parse.Parse

class HuertoApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Parse.initialize(
            Parse.Configuration.Builder(this).applicationId("7gDmYE8LothMW2eQCCzpKGST5tHOC8CFV2ICoZZL")
                .clientKey("vOUJHjjJUFpRzugQ80F4Qg6clFZia3lUcfi5OUtw")
                .server("https://parseapi.back4app.com/")
                .build()
        )

    }
}