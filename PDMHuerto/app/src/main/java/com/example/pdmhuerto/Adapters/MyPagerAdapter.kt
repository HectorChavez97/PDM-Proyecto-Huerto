package com.example.pdmhuerto.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.pdmhuerto.Fragments.ElementFragments.ElementDescriptionFragment
import com.example.pdmhuerto.Fragments.ElementFragments.ElementMapFragment
import com.example.pdmhuerto.Fragments.PerfilFragment
import com.parse.ParseObject

class MyPagerAdapter(fragment: FragmentManager, val element: ParseObject): FragmentPagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 ->           ElementDescriptionFragment(element)
            else -> return ElementMapFragment(element)
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Description"
            else -> return "Places"
        }
    }

}