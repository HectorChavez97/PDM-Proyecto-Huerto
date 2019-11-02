package com.example.pdmhuerto.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.pdmhuerto.R
import org.jetbrains.anko.find

class Login_Activity : AppCompatActivity(), View.OnClickListener {
    lateinit var mLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mLogin = find(R.id.activity_login_btn_login)

        mLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.activity_login_btn_login -> {
                val intent = Intent(this, Navigation_Activity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
