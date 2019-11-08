package com.example.pdmhuerto.Activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pdmhuerto.R
import kotlinx.android.synthetic.main.card_view_recycler.*
import org.jetbrains.anko.find
import java.util.jar.Manifest

class Register2_Activity : AppCompatActivity(), View.OnClickListener  {
    lateinit var userSetProfilePicture: ImageView
    lateinit var userShowName: TextView
    lateinit var finishRegistation: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2_)

        userSetProfilePicture = find(R.id.user_set_profilePic)
        userSetProfilePicture.setOnClickListener(this)

        userShowName = find(R.id.show_register_username)
        userShowName.setOnClickListener(this)

        finishRegistation = find(R.id.finish_registationButton)
        finishRegistation.setOnClickListener(this)

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
                        requestPermissions(permissions, PERMISION_CODE)
                    }

                    else pickImageFromGallery()
                }
                else pickImageFromGallery()
            }

            R.id.finish_registationButton -> {
                val intent = Intent(this, Navigation_Activity::class.java)
                startActivity(intent)
                finish()
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

        if(resultCode == Activity.RESULT_OK){
            Glide.with(this)
                .load(data?.data)
                .apply(RequestOptions.circleCropTransform())
                .into(userSetProfilePicture)
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

    companion object {
        private val IMAGE_CODE = 1000
        private val PERMISION_CODE = 1001
    }

}
