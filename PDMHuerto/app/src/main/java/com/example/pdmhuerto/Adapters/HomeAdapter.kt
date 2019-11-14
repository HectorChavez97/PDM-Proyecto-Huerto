package com.example.pdmhuerto.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pdmhuerto.R
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery

class HomeAdapter(val list: List<ParseObject>): RecyclerView.Adapter<HomeAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_recycler, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private var rm = Glide.with(view)

        fun bindItems(data: ParseObject){
            val title:          TextView  = itemView.findViewById(R.id.title)
            val description:    TextView  = itemView.findViewById(R.id.description)
            val image:          ImageView = itemView.findViewById(R.id.imageView)
            val profilePicture: ImageView = itemView.findViewById(R.id.user_profile_picture)
            val userName:       TextView  = itemView.findViewById(R.id.user_name)

            val img:     ParseFile  = data.getParseFile("image")!!
            val userN:   String     = data.getParseObject("postedBy")?.getString("username")!!
            val userImg: ParseFile  = data.getParseObject("postedBy")!!.getParseFile("profilePicture")!!


            userName.text    = userN
            title.text       = data.getString("title").toString()
            description.text = data.getString("description").toString()
            rm.load(img.data).into(image)
            rm.load(userImg.data).apply(RequestOptions.circleCropTransform()).into(profilePicture)
        }
    }

}