package com.example.pdmhuerto.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.pdmhuerto.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.jetbrains.anko.find

class Register_Activity : AppCompatActivity(), View.OnClickListener {
    lateinit var username: TextInputEditText
    lateinit var email: TextInputEditText
    lateinit var password: TextInputEditText
    lateinit var valPassword: TextInputEditText

    lateinit var nextButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        username = find(R.id.activity_register_tiet_usarname)
        email = find(R.id.activity_register_tiet_email)
        password = find(R.id.activity_register_tiet_password)
        valPassword = find(R.id.activity_register_tiet_valPassword)

        nextButton = find(R.id.button_registation2)

        nextButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.button_registation2 -> {
                when(validateData()){
                    ALL_CORRECT -> {
                        val intent = Intent(this, Register2_Activity::class.java)
                        intent.putExtra("username", username.text.toString())
                        intent.putExtra("email", email.text.toString())
                        intent.putExtra("password", password.text.toString())
                        startActivity(intent)
                        finish()
                    }

                    USER_MISSING ->{
                        Toast.makeText(this, "Please insert your username", Toast.LENGTH_SHORT).show()
                    }

                    EMAIL_MISSING -> {
                        Toast.makeText(this, "Please insert your email", Toast.LENGTH_SHORT).show()
                    }

                    PASSWORD_MISSING -> {
                        Toast.makeText(this, "Please insert your password", Toast.LENGTH_SHORT).show()
                    }

                    VALIDATEPASSWORD_MISSING -> {
                        Toast.makeText(this, "Please check your password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun validateData(): Int{
        if(username.text.toString() == "") return USER_MISSING
        if(email.text.toString() == "")    return EMAIL_MISSING
        if(password.text.toString() == "") return PASSWORD_MISSING
        if(valPassword.text.toString() == "") return VALIDATEPASSWORD_MISSING
        if(password.text.toString() != valPassword.text.toString()) return PASSWORD_INCORRECT

        return ALL_CORRECT
    }

    companion object {
        private val ALL_CORRECT = 1000
        private val USER_MISSING = 1001
        private val EMAIL_MISSING = 1002
        private val PASSWORD_MISSING = 1003
        private val VALIDATEPASSWORD_MISSING = 1004
        private val PASSWORD_INCORRECT = 1004
    }
}
