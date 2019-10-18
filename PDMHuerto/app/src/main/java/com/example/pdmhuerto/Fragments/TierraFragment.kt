package com.example.pdmhuerto.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.pdmhuerto.Activities.Navigation_Activity
import com.example.pdmhuerto.R

class TierraFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tierra, container, false)
        val backIcon: ImageView = view.findViewById(R.id.back)

        backIcon.setOnClickListener{
            (activity as Navigation_Activity).createFragment(CatalogoFragment())
        }

        return view
    }
}