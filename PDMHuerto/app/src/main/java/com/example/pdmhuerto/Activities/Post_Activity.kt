package com.example.pdmhuerto.Activities

import android.app.Activity
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toFile
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pdmhuerto.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.parse.*
import org.jetbrains.anko.find
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class Post_Activity : AppCompatActivity(), View.OnClickListener {
    private lateinit var button: Button

    lateinit var titleInfo: TextInputEditText
    lateinit var descriptionInfo: TextInputLayout
    lateinit var descriptionText: TextInputEditText
    lateinit var photoHolder: ImageView

    lateinit var photoButton: FloatingActionButton
    lateinit var cancelButton: FloatingActionButton
    lateinit var nextButton: FloatingActionButton

    private var imageFile: ParseFile? = null
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_)

        photoButton = find(R.id.photo_button)
        photoButton.setOnClickListener(this)

        cancelButton = find(R.id.cancel_button)
        cancelButton.setOnClickListener(this)

        nextButton = find(R.id.next_button)
        nextButton.setOnClickListener(this)

        titleInfo = find(R.id.event_title)
        descriptionInfo = find(R.id.post_description)
        descriptionText = find(R.id.post_description_text)
        photoHolder = find(R.id.photoHolder)

        createNotificationChannel()
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
                if(validateInfo() == COMPLETE) {
                    val intent = Intent(this, Navigation_Activity::class.java)
                    startActivity(intent)
                    finish()

                    val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle("Tu publicacion esta por publicarse")
                        .setContentText("En unos segundos su publicacion estara lista y podran verla tu y los demas usuarios")
                        .setStyle(NotificationCompat.BigTextStyle())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                    NotificationManagerCompat.from(this).notify(11, builder.build())
                }
            }
            R.id.photo_button -> {
               if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                   if(checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                       checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ){

                       val permission = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                       requestPermissions(permission, PERMISSIONS_ACCEPTED)
                   }

                   else openCamera()
               }

                else openCamera()
            }
        }
    }

    private fun openCamera(){
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New picture")
        values.put(MediaStore.Images.Media.TITLE, "From the Camera")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSIONS_ACCEPTED -> {
                if(grantResults.size >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) openCamera()

                else Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            IMAGE_CAPTURE_CODE -> {
                if(resultCode == Activity.RESULT_OK){
                    val image: Bitmap
                    Glide.with(this).load(imageUri).into(photoHolder)

                    if(Build.VERSION.SDK_INT < 28) {
                        image = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                    }

                    else{
                        val imageDecoder = ImageDecoder.createSource(this.contentResolver, imageUri!!)
                        image = ImageDecoder.decodeBitmap(imageDecoder)
                    }

                    val file = File(this.cacheDir, image.toString())
                    file.createNewFile()

                    val bos = ByteArrayOutputStream()
                    image.compress(Bitmap.CompressFormat.PNG, 0, bos)

                    val fos = FileOutputStream(file)
                    fos.write(bos.toByteArray())
                    fos.flush()
                    fos.close()
                    imageFile = ParseFile(file)
                }
            }

            else -> {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getDataError(): Int{
        if (titleInfo.text.toString() == "")      return TITLE_EMPTY
        if(descriptionText.text.toString() == "") return DESCRIPTION_EMPTY
        if(imageUri == null)                      return IMAGE_EMPTY

        return COMPLETE
    }

    private fun validateInfo(): Int {
        when(getDataError()){
            TITLE_EMPTY -> {
                Toast.makeText(this, "Please insert a title for your post", Toast.LENGTH_SHORT).show()
                return TITLE_EMPTY
            }
            DESCRIPTION_EMPTY ->{
                Toast.makeText(this, "Please insert a description for your post", Toast.LENGTH_SHORT).show()
                return DESCRIPTION_EMPTY
            }
            IMAGE_EMPTY -> {
                Toast.makeText(this, "No image found, please add one", Toast.LENGTH_SHORT).show()
                return IMAGE_EMPTY
            }
        }

        loadInfo()
        return COMPLETE
    }

    private fun loadInfo(){
        val userPointer = "L2csCmr04l"
        val post = ParseObject("Post")
        post.put("title", titleInfo.text.toString())
        post.put("description", descriptionText.text.toString())
        post.put("postedBy", ParseObject.createWithoutData("_User", userPointer))
        post.put("image", imageFile!!)

        post.saveInBackground()
    }   

    companion object{
        const val PERMISSIONS_ACCEPTED = 1000
        const val IMAGE_CAPTURE_CODE = 1001
        const val TITLE_EMPTY = 1
        const val DESCRIPTION_EMPTY = 2
        const val IMAGE_EMPTY  = 3
        const val COMPLETE = 10
        const val CHANNEL_ID = "AndroidCourse"
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "com.example.pdmhuerto",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

}
