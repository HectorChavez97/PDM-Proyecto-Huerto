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
            R.id.button_registation2 ->{
                if(validateLocalData() == ALL_CORRECT){
                    val name     = username.text.toString()
                    val email    = email.text.toString()
                    val password = password.text.toString()
                    if(!da(name, email)){
                        openActivity(name, email, password)
                    }
                }
            }
        }
    }

    private fun validateLocalData(): Int{
        if(username.text.toString() == ""){
            alertDisplayer("Error", "Please insert your username")
            return USER_MISSING
        }
        if(email.text.toString() == ""){
            alertDisplayer("Error", "Please insert your email")
            return EMAIL_MISSING
        }
        if(password.text.toString() == ""){
            alertDisplayer("Error", "Please insert your password")
            return PASSWORD_MISSING
        }
        if(valPassword.text.toString() == ""){
            alertDisplayer("Error", "Please check your password")
            return VALIDATE_PASSWORD_MISSING
        }
        if(password.text.toString() != valPassword.text.toString()){
            alertDisplayer("Error", "Passwords are not the same")
            return PASSWORD_INCORRECT
        }

        return ALL_CORRECT
    }

    private fun da(name: String, email: String): Boolean{
        var flag = false
        val query = ParseQuery.getQuery<ParseObject>("_User")

        query.findInBackground(object : FindCallback<ParseObject>{
            override fun done(userList: List<ParseObject>, e: ParseException?) {
                if (e == null) {
                    for(i in userList){
                        if(i.get("username").toString() == name){
                            alertDisplayer("Register Fail", "Username already in use")
                            flag = true
                        }
                        if(i.get("email").toString() == email){
                            alertDisplayer("Register Fail", "Email already in use")
                            flag = true
                        }
                    }
                }
            }
        })

        Log.d("Register.Activity", flag.toString())
        return flag
    }

    fun openActivity(name: String, email: String, password: String){
        val intent = Intent(this, Register2_Activity::class.java)
        intent.putExtra("username", name)
        intent.putExtra("email", email)
        intent.putExtra("password", password)
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
