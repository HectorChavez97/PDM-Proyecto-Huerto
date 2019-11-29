package com.example.pdmhuerto.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.pdmhuerto.R
import com.parse.ParseUser
import org.jetbrains.anko.find


class Start_Activity : AppCompatActivity(), View.OnClickListener {
    lateinit var mLogin: Button
    lateinit var mRegister: Button

    class toFinish{
        companion object{
            var activity: Activity? = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        toFinish.activity = this@Start_Activity
        super.onCreate(savedInstanceState)
        setContentView((R.layout.activity_start))

        mLogin = find(R.id.activity_login_button_start)
        mRegister = find(R.id.activity_register_button_start)

        mLogin.setOnClickListener(this)
        mRegister.setOnClickListener(this)

        userLogged()
    }

    private fun userLogged(){
        if(ParseUser.getCurrentUser() != null) {
            val intent = Intent(this, Navigation_Activity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.activity_login_button_start -> {
                val intent = Intent(this, Login_Activity::class.java)
                startActivity(intent)
            }

            R.id.activity_register_button_start -> {
                val intent = Intent(this, Register_Activity::class.java)
                startActivity(intent)
            }
        }
    }
}
