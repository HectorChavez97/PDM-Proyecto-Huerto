package com.example.pdmhuerto.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pdmhuerto.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.parse.ParseFile
import com.parse.ParseObject
import kotlinx.android.synthetic.main.activity_post_.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.io.File


class Post_Activity : AppCompatActivity(), View.OnClickListener {

    lateinit var titleInfo: TextInputEditText
    lateinit var descriptionInfo: TextInputLayout
    lateinit var descriptionText: TextInputEditText

    lateinit var imageFile: ParseFile

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
        descriptionText = find(R.id.post_description_text)

        photo_button.setOnClickListener(this)
        cancel_button.setOnClickListener(this)
        next_button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cancel_button -> {
                AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { _ , _->
                        val intent = Intent(this, Navigation_Activity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
            R.id.next_button -> {
                post()

                val intent = Intent(this, Navigation_Activity::class.java)
                startActivity(intent)
                finish()
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
                    val displayMetrics = DisplayMetrics()
                    windowManager.defaultDisplay.getMetrics(displayMetrics)

                    val image = data.extras?.get("data") as Bitmap
                    //imageFile = data.extras?.get("data") as ParseFile

                    var height = displayMetrics.heightPixels + 50
                    var width = displayMetrics.widthPixels

                    val sizedImage = Bitmap.createScaledBitmap(image, width, height/2, true)

                    photoHolder.setImageBitmap(sizedImage)
                }
            }

            else -> {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validateData(): Int{
        if(titleInfo.text.toString() == "") return TITLE_EMPTY
        else if(descriptionText.text.toString() == "") return DESCRIPTION_EMPTY
        return COMPLETE
    }

    private fun post() {
        when(validateData()){
            TITLE_EMPTY -> {
                Toast.makeText(this, "Ingresa un titulo!", Toast.LENGTH_SHORT).show()
            }
            DESCRIPTION_EMPTY ->{
                Toast.makeText(this, "Ingresa una descripcion!", Toast.LENGTH_SHORT).show()
            }
            IMAGE_EMPTY -> {
                Toast.makeText(this, "No imagen!", Toast.LENGTH_SHORT).show()
            }
            COMPLETE -> {
                Toast.makeText(this, "funciona", Toast.LENGTH_LONG).show()
                loadBack()
            }
        }
    }

    private fun loadBack(){
        val post = ParseObject("Post")
        post.put("Title", titleInfo.text.toString())
        post.put("Description", descriptionText.text.toString())

        post.saveInBackground()
    }

    companion object{
        const val CAMARA_REQUEST_ACCEPTED = 1000
        const val TITLE_EMPTY = 1
        const val DESCRIPTION_EMPTY = 2
        const val IMAGE_EMPTY  = 3
        const val COMPLETE = 10
    }

}
