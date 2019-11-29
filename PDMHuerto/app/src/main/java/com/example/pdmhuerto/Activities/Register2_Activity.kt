package com.example.pdmhuerto.Activities

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pdmhuerto.R
import com.parse.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class Register2_Activity : AppCompatActivity(), View.OnClickListener  {
    private lateinit var userSetProfilePicture: ImageView
    private lateinit var userShowName: TextView
    private lateinit var finishRegistration: Button
    private lateinit var parseFile: ParseFile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2_)

        userSetProfilePicture = find(R.id.user_set_profilePic)
        userSetProfilePicture.setOnClickListener(this)

        userShowName = find(R.id.show_register_username)
        userShowName.setOnClickListener(this)

        finishRegistration = find(R.id.finish_registationButton)
        finishRegistration.setOnClickListener(this)

        setDefaultInfo()
    }

    private fun setDefaultInfo() {
        Glide.with(this)
            .load(R.drawable.default_profile_picture)
            .apply(RequestOptions.circleCropTransform())
            .into(userSetProfilePicture)

        userShowName.text = intent.getStringExtra("username")
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.user_set_profilePic ->{
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        requestPermissions(permissions, PERMISSION_CODE)
                    }

                    else pickImageFromGallery()
                }
                else pickImageFromGallery()
            }

            R.id.finish_registationButton -> {
                registerUser()
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && data != null) {
            Glide.with(this).load(data.data).apply(RequestOptions.circleCropTransform()).into(userSetProfilePicture)

            val inputStream = contentResolver.openInputStream(data.data!!)
            val drawable = Drawable.createFromStream(inputStream, data.data!!.toString())

            val bitmap = drawable.toBitmap()
            val arrayBytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, arrayBytes)
            val parseIma = arrayBytes.toByteArray()

            var imageFile = ParseFile("userProfilePic.png", parseIma)
            imageFile.saveInBackground(object : SaveCallback{
                override fun done(e: ParseException?) {
                    parseFile = imageFile
                }
            })

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            Post_Activity.PERMISSIONS_ACCEPTED -> {
                if(grantResults.size >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) pickImageFromGallery()

                else Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(){
        ParseUser.getCurrentUser().put("profilePicture", parseFile)
        ParseUser.logOut()
        openActivity()
    }

    fun openActivity(){
        val intent = Intent(this, Login_Activity::class.java)
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
        private const val IMAGE_CODE = 1000
        private const val PERMISSION_CODE = 1001
    }

}
