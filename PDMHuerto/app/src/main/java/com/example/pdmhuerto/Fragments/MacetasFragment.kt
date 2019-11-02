package com.example.pdmhuerto.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pdmhuerto.Activities.Navigation_Activity
import com.example.pdmhuerto.Adapters.ProductViewAdapter
import com.example.pdmhuerto.Adapters.ProductViewHolder
import com.example.pdmhuerto.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MacetasFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_macetas, container, false)
        val backIcon: FloatingActionButton = root.findViewById(R.id.back)


        backIcon.setOnClickListener{
            (activity as Navigation_Activity).createFragment(CatalogoFragment())
        }

        val recyclerView: RecyclerView = root.findViewById(R.id.recycler_view_container)
        val arrayList =  ArrayList<ProductViewHolder>()
        arrayList.add(
            ProductViewHolder(
                "Producto 1",
                "Placeholder para macetas"
            )
        )
        arrayList.add(
            ProductViewHolder(
                "Producto 2",
                "Placeholder para macetas"
            )
        )
        arrayList.add(
            ProductViewHolder(
                "Producto 3",
                "Placeholder para macetas"
            )
        )

        val adapt = ProductViewAdapter(arrayList)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapt

        return root
    }
}