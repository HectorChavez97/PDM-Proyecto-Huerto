package com.example.pdmhuerto.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pdmhuerto.Activities.PopUpInfo_Activity
import com.example.pdmhuerto.Activities.Post_Activity
import com.example.pdmhuerto.R
import com.parse.ParseFile
import com.parse.ParseObject
import org.jetbrains.anko.find

class PostAdapter(val list: List<ParseObject>, val isProfile: Boolean): RecyclerView.Adapter<PostAdapter.ViewHolder>(), View.OnClickListener{
    lateinit var info: ImageView
    lateinit var like: ImageView
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_recycler, parent, false)

        context = parent.context
        info = v.find(R.id.configuration)
        like = v.find(R.id.like)

        if (isProfile){
            info.visibility = View.VISIBLE
        }
        else{
            like.visibility = View.VISIBLE
        }

        info.setOnClickListener(this)
        like.setOnClickListener(this)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.configuration -> {
                val intent = Intent(context, PopUpInfo_Activity::class.java)
                context.startActivity(intent)
            }
            R.id.like -> {
                Toast.makeText(context, "Like", Toast.LENGTH_LONG).show()
            }
        }
    }







    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val title: TextView           = itemView.findViewById(R.id.title)
        private val profilePicture: ImageView = itemView.findViewById(R.id.user_profile_picture)
        private val userName: TextView        = itemView.findViewById(R.id.user_name)
        private val description: TextView     = itemView.findViewById(R.id.description)
        private val image: ImageView          = itemView.findViewById(R.id.imageView)

        private var rm = Glide.with(view)

        fun bindItems(data: ParseObject){
            val img: ParseFile = data.getParseFile("image")!!
            val userN:   String     = data.getParseObject("postedBy")?.getString("username")!!
            val userImg: ParseFile = data.getParseObject("postedBy")!!.getParseFile("profilePicture")!!

            userName.text    = userN

            title.text       = data.getString("title").toString()
            description.text = data.getString("description").toString()
            rm.load(img.data).into(image)
            rm.load(userImg.data).apply(RequestOptions.circleCropTransform()).into(profilePicture)
        }
    }

}