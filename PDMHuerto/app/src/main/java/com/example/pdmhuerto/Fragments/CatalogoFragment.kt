package com.example.pdmhuerto.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pdmhuerto.R
import com.example.pdmhuerto.Activities.Navigation_Activity
import com.google.android.material.button.MaterialButton



class CatalogoFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_catalogo, container, false)


        val semButton: MaterialButton = root.findViewById(R.id.fragment_catalogo_bt_1)
        val herButton: MaterialButton = root.findViewById(R.id.fragment_catalogo_bt_2)
        val macButton: MaterialButton = root.findViewById(R.id.fragment_catalogo_bt_3)
        val tieButton: MaterialButton = root.findViewById(R.id.fragment_catalogo_bt_4)

        semButton.setOnClickListener{
            (activity as Navigation_Activity).createFragment(SemillasFragment())
        }

        herButton.setOnClickListener{
            (activity as Navigation_Activity).createFragment(HerramientasFragment())
        }

        tieButton.setOnClickListener{
            (activity as Navigation_Activity).createFragment(TierraFragment())
        }

        macButton.setOnClickListener{
            (activity as Navigation_Activity).createFragment(MacetasFragment())
        }

        return root
    }
}