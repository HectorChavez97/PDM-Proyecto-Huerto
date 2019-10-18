package com.example.pdmhuerto.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.pdmhuerto.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import org.jetbrains.anko.find

class Post_Activity : AppCompatActivity() {
    lateinit var titleInfo: TextInputEditText
    lateinit var descriptionInfo: TextInputEditText
    lateinit var cancel_button: FloatingActionButton
    lateinit var next_button: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_)

        cancel_button = find(R.id.cancel_button)
        next_button = find(R.id.next_button)
        titleInfo = find(R.id.post_title)
        descriptionInfo = find(R.id.post_description)


        val onclickListener: View.OnClickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.cancel_button -> {
                    val intent = Intent(this, Navigation_Activity::class.java)
                    startActivity(intent)
                }
                R.id.next_button -> {
                    val intent = Intent(this, Navigation_Activity::class.java)
                    startActivity(intent)
                }
            }
        }

        cancel_button.setOnClickListener(onclickListener)
        next_button.setOnClickListener(onclickListener)

    }

}
