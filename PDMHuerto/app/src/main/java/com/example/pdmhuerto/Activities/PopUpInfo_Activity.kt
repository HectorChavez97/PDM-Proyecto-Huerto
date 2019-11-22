package com.example.pdmhuerto.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.example.pdmhuerto.R

class PopUpInfo_Activity : AppCompatActivity() {
    var width = 0
    var height = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_up_info_)

        supportActionBar?.hide()

        popUpMetrics()
        window.setLayout((width * 0.8).toInt(), (height * 0.3).toInt())
    }

    private fun popUpMetrics(){
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        width   = dm.widthPixels
        height  = dm.heightPixels
    }
}
