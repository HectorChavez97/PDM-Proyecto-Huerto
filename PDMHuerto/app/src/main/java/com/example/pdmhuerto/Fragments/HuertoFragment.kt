package com.example.pdmhuerto.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pdmhuerto.Activities.AgregarHuerto_Activity
import com.example.pdmhuerto.Adapters.EventsCalendarAdapter
import com.example.pdmhuerto.R
import com.parse.*
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import java.nio.file.Files.find
import java.util.*
import kotlin.math.log


class HuertoFragment : Fragment(), View.OnClickListener {
    lateinit var add: ImageView
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_huerto, container, false)

        recyclerView  = root.find(R.id.recyclerView_events)
        add = root.find(R.id.next)
        add.setOnClickListener(this)

        val startDate: Calendar = Calendar.getInstance()
        val endDate: Calendar = Calendar.getInstance()

        startDate.add(Calendar.MONTH, -1)
        endDate.add(Calendar.MONTH, 1)

        val horizontalCalendar: HorizontalCalendar = HorizontalCalendar.Builder(root, R.id.calendarView)
            .range(startDate, endDate)
            .datesNumberOnScreen(7)
            .build()

        horizontalCalendar.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {
                val c = Calendar.getInstance()
                c.set(Calendar.YEAR, date!!.get(Calendar.YEAR))
                c.set(Calendar.MONTH, date!!.get(Calendar.MONTH))
                c.set(Calendar.DAY_OF_MONTH, date!!.get(Calendar.DAY_OF_MONTH))
                c.set(Calendar.HOUR_OF_DAY, 0)
                c.set(Calendar.MINUTE, 0)
                c.set(Calendar.SECOND, 0)
                c.set(Calendar.MILLISECOND, 0)

                doAsync{
                    val query = ParseQuery.getQuery<ParseObject>("Huerto")
                    query.whereEqualTo("date", c.time)

                    query.findInBackground(object : FindCallback<ParseObject> {
                        var events: List<ParseObject> = arrayListOf()

                        override fun done(postList: List<ParseObject>, e: ParseException?) {
                            if (e == null) {
                                events = postList
                                recyclerView.adapter = EventsCalendarAdapter(events)
                                recyclerView.adapter?.notifyDataSetChanged()
                                recyclerView.layoutManager = LinearLayoutManager(context)
                            }
                        }
                    })
                }
            }
        }

        /*  val intent = Intent(Intent.ACTION_INSERT).setData(CalendarContract.Events.CONTENT_URI).
            putExtra(CalendarContract.Events.TITLE, "Test calendar").
                putExtra(CalendarContract.Events.DESCRIPTION, "Evento para probar si esto sirve").
                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis()).
                putExtra(CalendarContract.EXTRA_EVENT_END_TIME, System.currentTimeMillis() + (60*60+1000))

            startActivity(intent) */

        return root
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.next -> {
                val intent = Intent(context, AgregarHuerto_Activity::class.java)
                startActivity(intent)
            }
        }
    }

}
