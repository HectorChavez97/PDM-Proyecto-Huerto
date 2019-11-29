package com.example.pdmhuerto.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.pdmhuerto.Adapters.ElementsPhotosAdapter
import com.example.pdmhuerto.Adapters.MyPagerAdapter
import com.example.pdmhuerto.Interfaces.ReplaceFragment
import com.example.pdmhuerto.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.parse.*
import kotlinx.android.synthetic.main.activity_element.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find

class Element_Activity : AppCompatActivity(), View.OnClickListener, ReplaceFragment {
    lateinit var back: FloatingActionButton
    lateinit var recyclerViewImages: RecyclerView
    lateinit var element: ParseObject

    private var isSemilla = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_element)
        supportActionBar?.hide()

        recyclerViewImages  = find(R.id.recyclerView_images)
        back                = find(R.id.back)
        back.setOnClickListener(this)

        isSemilla = intent.getStringExtra("className") == "Semillas"
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
                        fragmentAdapter()
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

    private fun fragmentAdapter(){
        val fragmentAdapter = MyPagerAdapter(supportFragmentManager, element)
        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.back -> {
                finish()
            }
        }
    }

    override fun createFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}
