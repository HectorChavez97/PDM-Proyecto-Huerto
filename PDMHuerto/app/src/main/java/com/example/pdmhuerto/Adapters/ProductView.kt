package com.example.pdmhuerto.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pdmhuerto.R

//Agregar constructor
class ProductViewHolder (val title: String, val description: String){

}

class ProductViewAdapter(val list: ArrayList<ProductViewHolder>): RecyclerView.Adapter<ProductViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_catalogo, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems(data: ProductViewHolder) {
            val title: TextView = itemView.findViewById(R.id.producto)
            val description: TextView = itemView.findViewById(R.id.descripcion)
            //ImageView

            title.text = data.title
            description.text = data.description
        }
    }
}