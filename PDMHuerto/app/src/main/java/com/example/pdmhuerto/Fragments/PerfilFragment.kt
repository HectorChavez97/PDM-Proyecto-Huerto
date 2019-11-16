package com.example.pdmhuerto.Fragments

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pdmhuerto.Activities.Register2_Activity
import com.example.pdmhuerto.Activities.Relacion_Usuario_Activity
import com.example.pdmhuerto.Activities.Start_Activity
import com.example.pdmhuerto.Adapters.ProfileAdapter
import com.example.pdmhuerto.R
import com.parse.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

class PerfilFragment: Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.seguidos -> {
                val intent = Intent(context, Relacion_Usuario_Activity::class.java)
                intent.putExtra("toSelect", "following")
                startActivity(intent)
            }
            R.id.seguidores -> {
                val intent = Intent(context, Relacion_Usuario_Activity::class.java)
                intent.putExtra("toSelect", "followers")
                startActivity(intent)
            }
            R.id.log_out -> {
                ParseUser.logOut()
                val intent = Intent(context, Start_Activity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_perfil, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.recycler_view_container)

        val profilePic: ImageView = root.findViewById(R.id.user_profile_picture)
        val followers: TextView = root.findViewById(R.id.seguidos)
        val following: TextView = root.findViewById(R.id.seguidores)
        val followersNum: TextView = root.findViewById(R.id.seguidores_n)
        val followingNum: TextView = root.findViewById(R.id.seguidos_n)
        val logOut: Button = root.findViewById(R.id.log_out)

        doAsync {
            val query = ParseQuery.getQuery<ParseObject>("Post")
            query.findInBackground(object : FindCallback<ParseObject> {
                var posts: List<ParseObject> = arrayListOf()

                override fun done(postList: List<ParseObject>, e: ParseException?) {
                    if (e == null) {
                        posts = postList
                        Collections.reverse(posts)
                        recyclerView.adapter = ProfileAdapter(posts)
                        recyclerView.adapter?.notifyDataSetChanged()
                        recyclerView.layoutManager = LinearLayoutManager(context)
                    }
                }
            })
        }

        followers.setOnClickListener(this)
        following.setOnClickListener(this)
        logOut.setOnClickListener(this)

        return root
    }
}