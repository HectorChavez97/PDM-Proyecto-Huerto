package com.example.pdmhuerto.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pdmhuerto.R
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import java.util.*
import kotlin.math.log


class HuertoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_huerto, container, false)

        val startDate: Calendar = Calendar.getInstance()
        val endDate: Calendar = Calendar.getInstance()

        startDate.add(Calendar.MONTH, -1)
        endDate.add(Calendar.MONTH, 1)

        val horizontalCalendar: HorizontalCalendar = HorizontalCalendar.Builder(root, R.id.calendarView)
            .range(startDate, endDate)
            .datesNumberOnScreen(5)
            .build()

        horizontalCalendar.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {
                date
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

}
