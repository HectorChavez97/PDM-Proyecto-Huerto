package com.example.pdmhuerto.Adapters

import android.content.Context
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
import org.jetbrains.anko.find
import java.util.*

class ElementsContentAdapter(val list: List<Any>): RecyclerView.Adapter<ElementsContentAdapter.ViewHolder>()  {
    lateinit var sup: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        sup = parent.context

        when(viewType){
            0 -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.element_description, parent, false)
                return ViewHolder(v)
            }
            1 -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.teeeeeeeeeeeeeeeest, parent, false)
                return ViewHolder(v)
            }
            2 -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.element_description, parent, false)
                return ViewHolder(v)
            }
        }

        val v = LayoutInflater.from(parent.context).inflate(R.layout.element_description, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int { // Just as an example, return 0 or 2 depending on position
        return position
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder.itemViewType){
            0 -> holder.bindInformation(list[position] as String)
            1 -> holder.bindDescription(list[position] as String)
            2 -> holder.bindInformation(list[position] as String)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private var rm = Glide.with(view)

        fun bindInformation(data: String){
            val information: TextView = itemView.find(R.id.descriptionTextView)
            information.text = data
        }

        fun bindDescription(data: String){
            val description: TextView = itemView.find(R.id.descriptionTextView)
            description.text = data
        }
    }


}