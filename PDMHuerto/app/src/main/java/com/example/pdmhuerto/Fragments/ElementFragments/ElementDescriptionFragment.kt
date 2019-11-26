package com.example.pdmhuerto.Fragments.ElementFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.pdmhuerto.R
import com.parse.ParseObject
import org.jetbrains.anko.find

class ElementDescriptionFragment(val element: ParseObject) : Fragment() {
    lateinit var titleTextView: TextView
    lateinit var descriptionTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_element_description, container, false)

        titleTextView       = v.find(R.id.elementTitle)
        titleTextView.text  = element.getString("nombre")

        descriptionTextView      = v.find(R.id.elementDescription)
        descriptionTextView.text = element.getString("descripcion")

        return v
    }

}
