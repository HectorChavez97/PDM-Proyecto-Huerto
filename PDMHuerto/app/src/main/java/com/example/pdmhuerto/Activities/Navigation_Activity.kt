package com.example.pdmhuerto.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.pdmhuerto.Fragments.*
import com.example.pdmhuerto.R
import com.example.pdmhuerto.Interfaces.ReplaceFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class Navigation_Activity : AppCompatActivity(),ReplaceFragment {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        /*val root = inflater.inflate(R.layout.fragment_catalogo, container, false)
        val semButton: MaterialButton = findViewById(R.id.fragment_catalogo_bt_1)
        val herButton: MaterialButton = findViewById(R.id.fragment_catalogo_bt_2)
        val tieButton: MaterialButton = findViewById(R.id.fragment_catalogo_bt_3)
        val macButton: MaterialButton = findViewById(R.id.fragment_catalogo_bt_4)*/

        var navView : BottomNavigationView  = findViewById(R.id.bottom_navigation)
        createFragment(HomeFragment())

        navView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.home -> {
                    createFragment(HomeFragment())
                }

                R.id.perfil -> {
                    createFragment(PerfilFragment())
                }

                R.id.catalogo -> {
                    createFragment(CatalogoFragment())
                }

                R.id.huerto -> {
                    createFragment(HuertoFragment())
                }
         }

         true
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