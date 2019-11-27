package com.example.pdmhuerto.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.pdmhuerto.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import org.jetbrains.anko.find
import androidx.appcompat.app.AlertDialog
import com.parse.*


class Register_Activity : AppCompatActivity(), View.OnClickListener {
    private lateinit var username: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var valPassword: TextInputEditText

    private lateinit var nextButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        username    = find(R.id.activity_register_tiet_usarname)
        email       = find(R.id.activity_register_tiet_email)
        password    = find(R.id.activity_register_tiet_password)
        valPassword = find(R.id.activity_register_tiet_valPassword)
        nextButton  = find(R.id.button_registation2)
        nextButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.button_registation2 -> registerUser()
        }
    }

    private fun registerUser(){
        val name        = username.text.toString()
        val email       = email.text.toString()

        val password    = password.text.toString()
        val passwordVal = valPassword.text.toString()

        if(validatePassword(password, passwordVal)){
            val user = ParseUser()
            user.username   = name
            user.email      = email
            user.setPassword(password)

            user.saveInBackground()

            user.signUpInBackground(object: SignUpCallback {
                override fun done(e: ParseException?) {
                    if(e == null){
                        openActivity(name)
                    }
                    else{
                        alertDisplayer("Register Fail", "${e.message} Please Try Again")
                    }
                }
            })
        }
    }

    private fun validatePassword(pass1: String, pass2: String): Boolean{
        return pass1 == pass2
    }

    fun openActivity(name: String){
        val intent = Intent(this, Register2_Activity::class.java)
        intent.putExtra("username", name)
        startActivity(intent)
        finish()
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
        private const val EMAIL_MISSING = 1002
        private const val PASSWORD_MISSING = 1003
        private const val VALIDATE_PASSWORD_MISSING = 1004
        private const val PASSWORD_INCORRECT = 1004
    }
}
