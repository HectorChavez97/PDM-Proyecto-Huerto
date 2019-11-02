package com.example.pdmhuerto.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pdmhuerto.Adapters.EventViewAdapter
import com.example.pdmhuerto.Adapters.EventViewHolder
import com.example.pdmhuerto.Adapters.ProductViewAdapter
import com.example.pdmhuerto.Adapters.ProductViewHolder
import com.example.pdmhuerto.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.jetbrains.anko.startActivity

class Day_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)

        val addButton: FloatingActionButton = findViewById(R.id.button_create_post)

        addButton.setOnClickListener{
            val intent = Intent(this, New_Event_Activity::class.java)
            startActivity(intent)
        }

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_container)
        val arrayList =  ArrayList<EventViewHolder>()
        arrayList.add(
            EventViewHolder(
                "Evento 1",
                "Status",
                "hora"
            )
        )
        arrayList.add(
            EventViewHolder(
                "Evento 2",
                "Status",
                "hora"
            )
        )
        arrayList.add(
            EventViewHolder(
                "Evento 3",
                "Status",
                "hora"
            )
        )

        val adapt = EventViewAdapter(arrayList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapt

    }
}
