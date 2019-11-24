package com.example.pdmhuerto.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pdmhuerto.Activities.Navigation_Activity
import com.example.pdmhuerto.Adapters.ElementsAdapter
import com.example.pdmhuerto.Interfaces.ReplaceFragment
import com.example.pdmhuerto.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find


//ElementFragment mostrara las semillas o herramientas o tierra o macetas.

class ElementFragment(val type: String) : Fragment(), View.OnClickListener, ReplaceFragment {
    lateinit var backIcon: FloatingActionButton
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_element, container, false)

        recyclerView = root.find(R.id.recycler_view_container)
        backIcon     = root.find(R.id.back)
        backIcon.setOnClickListener(this)

        getData(type)

        return root
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.back ->  createFragment(CatalogoFragment())
        }
    }

    private fun getData(parseClass: String){
        doAsync {
            val query = ParseQuery.getQuery<ParseObject>(parseClass)

            query.findInBackground(object : FindCallback<ParseObject> {
                var posts: List<ParseObject> = arrayListOf()

                override fun done(postList: List<ParseObject>, e: ParseException?) {
                    if (e == null) {
                        var isSemilla = if (parseClass == "Semillas") true else false

                        posts = postList
                        recyclerView.adapter = ElementsAdapter(posts, isSemilla)
                        recyclerView.adapter?.notifyDataSetChanged()
                        recyclerView.layoutManager = LinearLayoutManager(context)
                    }
                }
            })
        }
    }

    override fun createFragment(fragment: Fragment) {
        fragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragment_container, fragment)
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            ?.commit()
    }
}
