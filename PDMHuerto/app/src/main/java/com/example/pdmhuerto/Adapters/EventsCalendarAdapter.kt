package com.example.pdmhuerto.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pdmhuerto.Interfaces.ElementCardViewListener
import com.example.pdmhuerto.R
import com.parse.ParseObject
import org.jetbrains.anko.find

class EventsCalendarAdapter(val list: List<ParseObject>, OnCardViewListener: ElementCardViewListener): RecyclerView.Adapter<EventsCalendarAdapter.ViewHolder>() {
    private val onCardViewListener = OnCardViewListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_event, parent, false)

        return ViewHolder(v, onCardViewListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    class ViewHolder(view: View, OnCardViewListener: ElementCardViewListener): RecyclerView.ViewHolder(view), View.OnClickListener{
        private var semila: TextView = view.find(R.id.semila)
        private var tierra: TextView = view.find(R.id.tierra)
        private var maceta: TextView = view.find(R.id.maceta)
        private val calendar:ImageView = view.find(R.id.calendar)
        private val onCardViewListener = OnCardViewListener

        init{
            calendar.setOnClickListener(this)
        }

        fun bindItems(data: ParseObject){
            semila.text = data.getString("Semilla")
            tierra.text = data.getString("Tierra")
            maceta.text = data.getString("Maceta")
        }

        override fun onClick(v: View?) {
            onCardViewListener.onCardViewClick(adapterPosition)
        }
    }
}