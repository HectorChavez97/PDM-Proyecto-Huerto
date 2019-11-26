package com.example.pdmhuerto.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.pdmhuerto.Adapters.ElementsContentAdapter
import com.example.pdmhuerto.Adapters.ElementsPhotosAdapter
import com.example.pdmhuerto.Adapters.PostAdapter
import com.example.pdmhuerto.R
import com.parse.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import java.util.*

class Element_Activity : AppCompatActivity(), View.OnClickListener {
    lateinit var back: ImageView
    lateinit var next: ImageView
    lateinit var recyclerViewImages: RecyclerView
    lateinit var recyclerViewContent: RecyclerView
    lateinit var element: ParseObject

    var isSemilla = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_element)
        supportActionBar?.hide()

        recyclerViewImages  = find(R.id.recyclerView_images)
        recyclerViewContent = find(R.id.recyclerView_content)
        back                = find(R.id.back)
        next                = find(R.id.next)

        isSemilla = intent.getStringExtra("className") == "Semillas"

        if(isSemilla){
            next.visibility = View.VISIBLE
        }

        back.setOnClickListener(this)
        next.setOnClickListener(this)

        getData(intent.getStringExtra("id")!!, intent.getStringExtra("className")!!)
    }

    private fun getData(elementId: String, className: String){
        doAsync {
            val query = ParseQuery.getQuery<ParseObject>(className)
            query.whereEqualTo(ParseObject.KEY_OBJECT_ID, elementId)

            query.getFirstInBackground(object :GetCallback<ParseObject> {
                override fun done(o: ParseObject?, e: ParseException?) {
                    if(o != null && e == null){
                        element = o
                        recyclerViewImages()
                        recyclerViewContent()
                    }
                }
            })
        }
    }

    private fun recyclerViewImages(){
        val pagerSnapHelper = PagerSnapHelper()
        val photos = arrayListOf(
            element.getParseFile("foto1")!!,
            element.getParseFile("foto2")!!,
            element.getParseFile("foto3")!!)

        recyclerViewImages.adapter = ElementsPhotosAdapter(photos)
        recyclerViewImages.adapter?.notifyDataSetChanged()
        recyclerViewImages.layoutManager = LinearLayoutManager(parent, LinearLayoutManager.HORIZONTAL, false)
        pagerSnapHelper.attachToRecyclerView(recyclerViewImages)
    }

    private fun recyclerViewContent(){
        val pagerSnapHelper = PagerSnapHelper()
        val photos = arrayListOf(
            element.get("descripcion")!!,
            element.get("nombre")!!)

        recyclerViewContent.adapter = ElementsContentAdapter(photos)
        recyclerViewContent.adapter?.notifyDataSetChanged()
        recyclerViewContent.layoutManager = LinearLayoutManager(parent, LinearLayoutManager.HORIZONTAL, false)
        pagerSnapHelper.attachToRecyclerView(recyclerViewContent)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.back -> {
                val intent = Intent(this, Navigation_Activity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
