package com.example.pdmhuerto.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pdmhuerto.R
import com.parse.ParseObject
import org.jetbrains.anko.find

class EventsCalendarAdapter(val list: List<ParseObject>): RecyclerView.Adapter<EventsCalendarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_event, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var semila: TextView = view.find(R.id.semila)
        var tierra: TextView = view.find(R.id.tierra)
        var maceta: TextView = view.find(R.id.maceta)

        fun bindItems(data: ParseObject){
            semila.text = data.getString("Semilla")
            tierra.text = data.getString("Tierra")
            maceta.text = data.getString("Maceta")
        }
    }
}