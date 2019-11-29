package com.example.pdmhuerto.Fragments

import android.content.Intent
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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pdmhuerto.Activities.Start_Activity
import com.example.pdmhuerto.Adapters.PostAdapter
import com.example.pdmhuerto.R
import com.parse.*
import org.jetbrains.anko.doAsync
import java.util.*

class PerfilFragment: Fragment(), View.OnClickListener {
    lateinit var profilePic: ImageView
    lateinit var userName: TextView
    lateinit var followers: TextView
    lateinit var following: TextView
    lateinit var followersNum: TextView
    lateinit var followingNum: TextView
    lateinit var logOut: Button

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_perfil, container, false)

        recyclerView = root.findViewById(R.id.recycler_view_container)
        profilePic   = root.findViewById(R.id.user_profile_picture)
        userName     = root.findViewById(R.id.user_name)
        logOut       = root.findViewById(R.id.log_out)

        logOut.setOnClickListener(this)


        getUserPost()
        setUserName()
        setUserImage()

        return root
    }

    private fun getUserPost(){
        doAsync {
            val query = ParseQuery.getQuery<ParseObject>("Post")
            query.include("postedBy")
            query.whereEqualTo("postedBy", ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().objectId))

            query.findInBackground(object : FindCallback<ParseObject> {
                var posts: List<ParseObject> = arrayListOf()

                override fun done(postList: List<ParseObject>, e: ParseException?) {
                    if (e == null) {
                        posts = postList
                        Collections.reverse(posts)
                        recyclerView.adapter = PostAdapter(posts, true)
                        recyclerView.adapter?.notifyDataSetChanged()
                        recyclerView.layoutManager = LinearLayoutManager(context)
                    }
                }
            })
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.log_out -> {
                ParseUser.logOut()
                val intent = Intent(context, Start_Activity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setUserName(){
        userName.text = ParseUser.getCurrentUser().get("username").toString()
    }

    private fun setUserImage(){
        val image = ParseUser.getCurrentUser().get("profilePicture") as ParseFile
        Glide.with(context!!).load(image.data).apply(RequestOptions.circleCropTransform()).into(profilePic)
    }

}