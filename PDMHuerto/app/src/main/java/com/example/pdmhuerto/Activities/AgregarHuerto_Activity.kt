package com.example.pdmhuerto.Activities

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.pdmhuerto.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import org.jetbrains.anko.find
import java.util.*
import kotlin.collections.ArrayList

class AgregarHuerto_Activity : AppCompatActivity(), View.OnClickListener {
    lateinit var spinnerSemilla: Spinner
    lateinit var spinnerMaceta: Spinner
    lateinit var spinnerTierra: Spinner
    lateinit var datePicker: Button
    lateinit var nextButton: FloatingActionButton

    lateinit var dateSelected: Date
    lateinit var semillaSelected: String
    lateinit var macetaSelected: String
    lateinit var tierraSelected: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_huerto)

        val semillas     = getData("Semillas")
        val macetas      = getData("Macetas")
        val tierra       = getData("Tierra")

        nextButton = find(R.id.button_create_event)
        datePicker = find(R.id.datePicker)

        spinnerSemilla = find(R.id.spinnerSemilla)
        spinnerSemilla.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, semillas)
        spinnerSemilla.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                semillaSelected = parent?.getItemAtPosition(position).toString()
            }
        }


        spinnerMaceta = find(R.id.spinnerMaceta)
        spinnerMaceta.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tierra)
        spinnerMaceta.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                macetaSelected = parent?.getItemAtPosition(position).toString()
            }
        }

        spinnerTierra = find(R.id.spinnerTierra)
        spinnerTierra.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, macetas)
        spinnerTierra.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tierraSelected = parent?.getItemAtPosition(position).toString()
            }
        }

        nextButton.setOnClickListener(this)
        datePicker.setOnClickListener(this)
    }


    private fun getData(className: String): ArrayList<String>{
        val query = ParseQuery<ParseObject>(className)
        val array = query.find()
        val arrayName = arrayListOf("...")

        array.forEach {
            arrayName.add(it.getString("nombre")!!)
        }

        return arrayName
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.datePicker -> showDate()

            R.id.button_create_event -> a()
        }
    }

    private fun a(){
        val userPointer = ParseUser.getCurrentUser().objectId

        val post = ParseObject("Huerto")
        post.put("Semilla", semillaSelected)
        post.put("Maceta", macetaSelected)
        post.put("Tierra", tierraSelected)
        post.put("date", dateSelected)
        post.put("postedBy", ParseObject.createWithoutData("_User", userPointer))

        post.saveInBackground()
    }

    private fun showDate(){
        val c = Calendar.getInstance()
        val day    = c.get(Calendar.DAY_OF_MONTH)
        val year   = c.get(Calendar.YEAR)
        val month  = c.get(Calendar.MONTH)

        val datePick = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{ _, year, month, dayOfMonth ->
                val c = Calendar.getInstance()
                c.set(Calendar.YEAR, year)
                c.set(Calendar.MONTH, month)
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                c.set(Calendar.HOUR_OF_DAY, 0)
                c.set(Calendar.MINUTE, 0)
                c.set(Calendar.SECOND, 0)
                c.set(Calendar.MILLISECOND, 0)

                dateSelected = c.time
            }, year, month, day)

        datePick.show()
    }
}
