package com.example.pdmhuerto.Fragments

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pdmhuerto.R


class HuertoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_huerto, container, false)

        val intent = Intent(Intent.ACTION_INSERT).setData(CalendarContract.Events.CONTENT_URI).
            putExtra(CalendarContract.Events.TITLE, "Test calendar").
            putExtra(CalendarContract.Events.DESCRIPTION, "Evento para probar si esto sirve").
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis()).
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, System.currentTimeMillis() + (60*60+1000))

        startActivity(intent)

        return root
    }

}
