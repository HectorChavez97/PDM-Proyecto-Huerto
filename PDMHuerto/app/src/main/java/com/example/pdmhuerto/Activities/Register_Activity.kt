package com.example.pdmhuerto.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.pdmhuerto.R
import org.jetbrains.anko.find

class Register_Activity : AppCompatActivity(), View.OnClickListener {
    lateinit var mRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mRegister = find(R.id.activity_register_btn_register)

        mRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.activity_register_btn_register -> {
                val intent = Intent(this, Navigation_Activity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
