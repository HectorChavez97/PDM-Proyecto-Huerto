package com.example.pdmhuerto.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pdmhuerto.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class New_Event_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_event)

        val cancelButton: FloatingActionButton = findViewById(R.id.cancel_button)

        cancelButton.setOnClickListener{
            val intent = Intent(this, Day_Activity::class.java)
            startActivity(intent)
        }
    }
}
