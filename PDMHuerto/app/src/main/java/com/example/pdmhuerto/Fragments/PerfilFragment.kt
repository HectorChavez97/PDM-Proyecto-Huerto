package com.example.pdmhuerto.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pdmhuerto.Adapters.CardViewAdapter
import com.example.pdmhuerto.Adapters.CardViewHolder
import com.example.pdmhuerto.R

class PerfilFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_perfil, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.recycler_view_container)
        val arrayList =  ArrayList<CardViewHolder>()
        arrayList.add(
            CardViewHolder(
                "Huerto 1",
                "Esta es el huerto numero 1 que hago, ojala tenga mucha suerte con esto"
            )
        )
        arrayList.add(
            CardViewHolder(
                "Huerto 2",
                "Esta es el huerto numero 1 que hago, ojala tenga mucha suerte con esto"
            )
        )

        val adapt = CardViewAdapter(arrayList)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapt


        return root
    }
}