package com.example.pdmhuerto.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.pdmhuerto.Fragments.HomeFragment
import com.example.pdmhuerto.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class Navigation_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        var navView : BottomNavigationView  = findViewById(R.id.bottom_navigation)
        createFragment(HomeFragment())

        navView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.home -> {
                    createFragment(HomeFragment())
                }

                R.id.perfil -> {

                }

                R.id.catalogo -> {

                }

                R.id.huerto -> {

                }
         }

         true
        }

    }

    private fun createFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
}