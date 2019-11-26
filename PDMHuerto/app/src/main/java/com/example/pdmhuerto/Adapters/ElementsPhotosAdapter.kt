package com.example.pdmhuerto.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pdmhuerto.R
import com.parse.ParseFile
import org.jetbrains.anko.find

class ElementsPhotosAdapter(val list: List<ParseFile>): RecyclerView.Adapter<ElementsPhotosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.element_photos, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val image: ImageView = itemView.find(R.id.photo_element)

        private var rm = Glide.with(view)

        fun bindItems(data: ParseFile){
            val postImg: ParseFile = data
            rm.load(postImg.data).into(image)
        }
    }

}