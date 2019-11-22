package com.example.pdmhuerto.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pdmhuerto.Activities.Post_Activity
import com.example.pdmhuerto.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.content.Intent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pdmhuerto.Adapters.HomeAdapter
import com.example.pdmhuerto.Adapters.PostAdapter
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*


class HomeFragment : Fragment(), View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    lateinit var recyclerView: RecyclerView
    lateinit var addButton: FloatingActionButton
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView        = root.findViewById(R.id.recycler_view_container)
        addButton           = root.findViewById(R.id.button_create_post)
        mSwipeRefreshLayout = root.findViewById(R.id.pullToRefresh)

        addButton.setOnClickListener(this)
        mSwipeRefreshLayout.setOnRefreshListener(this)
        getPosts()

        return root
    }

    override fun onRefresh() {
        getPosts()
    }

    override fun onClick(v: View?) {
        val intent = Intent(activity, Post_Activity::class.java)
        startActivity(intent)
    }

    private fun getPosts(){
        doAsync {
            val query = ParseQuery.getQuery<ParseObject>("Post")
            query.include("postedBy")

            query.findInBackground(object : FindCallback<ParseObject> {
                var posts: List<ParseObject> = arrayListOf()

                override fun done(postList: List<ParseObject>, e: ParseException?) {
                    if (e == null) {
                        posts = postList
                        Collections.reverse(posts)
                        recyclerView.adapter = PostAdapter(posts, false)
                        recyclerView.adapter?.notifyDataSetChanged()
                        recyclerView.layoutManager = LinearLayoutManager(context)
                        mSwipeRefreshLayout.isRefreshing = false
                    }
                }
            })
        }
    }

}
