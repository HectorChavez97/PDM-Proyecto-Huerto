package com.example.pdmhuerto.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pdmhuerto.R

//Agregar constructor
class EventViewHolder (val event: String, val status: String, val hora:String){

}

class EventViewAdapter(val list: ArrayList<EventViewHolder>): RecyclerView.Adapter<EventViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_eventos, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems(data: EventViewHolder) {
            val event: TextView = itemView.findViewById(R.id.evento)
            val status: TextView = itemView.findViewById(R.id.status)
            val hora: TextView = itemView.findViewById(R.id.hora)
            //ImageView

            event.text = data.event
            status.text = data.status
            hora.text = data.hora
        }
    }
}
