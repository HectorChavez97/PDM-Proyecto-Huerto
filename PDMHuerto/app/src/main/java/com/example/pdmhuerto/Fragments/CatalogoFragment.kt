package com.example.pdmhuerto.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pdmhuerto.R
import com.example.pdmhuerto.Activities.Navigation_Activity
import com.example.pdmhuerto.Interfaces.ReplaceFragment
import com.google.android.material.button.MaterialButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import org.jetbrains.anko.find


class CatalogoFragment: Fragment(), View.OnClickListener, ReplaceFragment{
    lateinit var semillasButton: MaterialButton
    lateinit var herramientasButton: MaterialButton
    lateinit var macetasButton: MaterialButton
    lateinit var tierraButton: MaterialButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var root = inflater.inflate(R.layout.fragment_catalogo, container, false)

        semillasButton     = root.find(R.id.semillas_button)
        semillasButton.setOnClickListener(this)

        herramientasButton = root.find(R.id.herramientas_button)
        herramientasButton.setOnClickListener(this)

        macetasButton      = root.find(R.id.macetas_button)
        macetasButton.setOnClickListener(this)

        tierraButton       = root.find(R.id.tierra_button)
        tierraButton.setOnClickListener(this)

        return root
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.semillas_button ->      createFragment(ElementFragment("Semillas"))
            R.id.herramientas_button ->  createFragment(ElementFragment("Herramientas"))
            R.id.macetas_button ->       createFragment(ElementFragment("Macetas"))
            R.id.tierra_button ->        createFragment(ElementFragment("Tierra"))
        }
    }

    override fun createFragment(fragment: Fragment) {
        fragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragment_container, fragment)
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ?.commit()
    }

}

