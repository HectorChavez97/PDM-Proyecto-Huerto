package com.example.pdmhuerto.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.pdmhuerto.Fragments.*
import com.example.pdmhuerto.Interfaces.ReplaceFragment
import com.example.pdmhuerto.R

class Relacion_Usuario_Activity : AppCompatActivity(), ReplaceFragment, View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relacion_usuario)
        supportActionBar?.hide()

        if(intent.getStringExtra("toSelect") == "following") createFragment(SeguidosFragment())
        else createFragment(SeguidoresFragment())
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.seguidos-> createFragment(SeguidoresFragment())
            R.id.seguidores-> createFragment(SeguidoresFragment())
        }
    }

    override fun createFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
}
