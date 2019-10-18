package com.example.pdmhuerto.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pdmhuerto.Activities.Post_Activity
import com.example.pdmhuerto.Adapters.CardViewAdapter
import com.example.pdmhuerto.Adapters.CardViewHolder
import com.example.pdmhuerto.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.content.Intent



class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.recycler_view_container)
        val addButton: FloatingActionButton = root.findViewById(R.id.button_create_post)

        addButton.setOnClickListener{
            val intent = Intent(activity, Post_Activity::class.java)
            startActivity(intent)
        }

        val arrayList =  ArrayList<CardViewHolder>()
        arrayList.add(
            CardViewHolder(
                "Huerto 1, que emocion!",
                "Esta es el huerto numero 1 que hago, ojala tenga mucha suerte con esto"
            )
        )
        arrayList.add(
            CardViewHolder(
                "Murio mi huerto",
                "Mi primer huerto se seco y tengo que empezar de nuevo"
            )
        )
        arrayList.add(
            CardViewHolder(
                "Empezando de nuevo con mi huerto",
                "Este es el segundo huerto que hago, ojala funcione"
            )
        )
        arrayList.add(
            CardViewHolder(
                "Me rindo",
                "Se volvio a morir todo el huerto, me doy por vencido con las plantitas"
            )
        )
        arrayList.add(
            CardViewHolder(
                "Tips?",
                "Alguien ayudeme con esto del huerto, soy muy malo"
            )
        )

        val adapt = CardViewAdapter(arrayList)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapt

        return root
    }
}
