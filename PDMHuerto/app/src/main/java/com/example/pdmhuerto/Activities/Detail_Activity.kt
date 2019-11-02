package com.example.pdmhuerto.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.pdmhuerto.R
import android.content.Intent


class Detail_Activity : AppCompatActivity() {

    lateinit var mBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

    }
}
