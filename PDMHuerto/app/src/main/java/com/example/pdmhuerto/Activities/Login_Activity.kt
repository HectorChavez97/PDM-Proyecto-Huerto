package com.example.pdmhuerto.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText
import com.parse.ParseUser
import org.jetbrains.anko.find
import com.example.pdmhuerto.R


class Login_Activity : AppCompatActivity(), View.OnClickListener {
    lateinit var mLogin: Button
    lateinit var user: TextInputEditText
    lateinit var password: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mLogin = find(R.id.activity_login_btn_login)
        user = find(R.id.login_username)
        password = find(R.id.login_password)

        mLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.activity_login_btn_login -> {
                if(validateLocalData() == ALL_CORRECT){
                    val name     = user.text.toString()
                    val password = password.text.toString()

                    validateUserLogin(name, password)
                }
            }
        }
    }

    private fun validateLocalData():Int{
        if(user.text.toString() == ""){
            alertDisplayer("Error", "Please insert your username")
            return USER_MISSING
        }
        if(password.text.toString() == ""){
            alertDisplayer("Error", "Please check your password")
            return PASSWORD_MISSING
        }

        return ALL_CORRECT
    }


    private fun validateUserLogin(username: String, password: String){
        ParseUser.logInInBackground(username, password) { parseUser, e ->
            if (parseUser != null) {
                val intent = Intent(this, Navigation_Activity::class.java)
                startActivity(intent)
                finish()
            } else {
                alertDisplayer("Login Failed", "${e.message}Please Try Again")
            }
        }
    }

    fun alertDisplayer(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)

        builder.setNegativeButton(android.R.string.no) { _, _->
            Toast.makeText(applicationContext,
                android.R.string.no, Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }

    companion object {
        private const val ALL_CORRECT = 1000
        private const val USER_MISSING = 1001
        private const val PASSWORD_MISSING = 1002
    }
}
