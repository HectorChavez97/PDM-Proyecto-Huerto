package com.example.pdmhuerto.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pdmhuerto.R

//Agregar constructor
class CardViewHolder (val title: String, val description: String){

}

class CardViewAdapter(val list: ArrayList<CardViewHolder>): RecyclerView.Adapter<CardViewAdapter.ViewHolder>(){
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

        fun bindItems(data: CardViewHolder){
            val title: TextView = itemView.findViewById(R.id.title)
            val description: TextView = itemView.findViewById(R.id.description)
            //ImageView

            title.text = data.title
            description.text = data.description
        }
    }

}