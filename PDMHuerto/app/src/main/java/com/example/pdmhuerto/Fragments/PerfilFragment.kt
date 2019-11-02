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
import com.example.pdmhuerto.R
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PerfilFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_perfil, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.recycler_view_container)

        doAsync {
            val query = ParseQuery.getQuery<ParseObject>("Post")
            query.findInBackground(object : FindCallback<ParseObject> {
                var posts: List<ParseObject> = arrayListOf()

                override fun done(postList: List<ParseObject>, e: ParseException?) {
                    if (e == null) {
                        uiThread{
                            posts = postList
                            recyclerView.adapter = CardViewAdapter(posts)
                            recyclerView.adapter?.notifyDataSetChanged()
                            recyclerView.layoutManager = LinearLayoutManager(context)
                        }
                    }
                }
            })
        }

        return root
    }
}