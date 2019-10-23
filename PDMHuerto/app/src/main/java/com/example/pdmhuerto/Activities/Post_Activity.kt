package com.example.pdmhuerto.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pdmhuerto.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_post_.*
import org.jetbrains.anko.find

class Post_Activity : AppCompatActivity(), View.OnClickListener {

    lateinit var titleInfo: TextInputEditText
    lateinit var descriptionInfo: TextInputLayout

    lateinit var photo_button: FloatingActionButton
    lateinit var cancel_button: FloatingActionButton
    lateinit var next_button: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_)

        photo_button = find(R.id.photo_button)
        cancel_button = find(R.id.cancel_button)
        next_button = find(R.id.next_button)

        titleInfo = find(R.id.post_title)
        descriptionInfo = find(R.id.post_description)

        photo_button.setOnClickListener(this)
        cancel_button.setOnClickListener(this)
        next_button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cancel_button -> {
                val intent = Intent(this, Navigation_Activity::class.java)
                startActivity(intent)
            }
            R.id.next_button -> {
                val intent = Intent(this, Navigation_Activity::class.java)
                startActivity(intent)
            }
            R.id.photo_button -> {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                //Existe aplicacion que soporta fotos
                if(intent.resolveActivity(packageManager) != null){
                    startActivityForResult(intent, CAMARA_REQUEST_ACCEPTED)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            CAMARA_REQUEST_ACCEPTED -> {
                if(resultCode == Activity.RESULT_OK && data != null){
                    photoHolder.setImageBitmap(data.extras?.get("data") as Bitmap)
                }
            }

            else -> {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object{
        const val CAMARA_REQUEST_ACCEPTED = 1000
    }

}
