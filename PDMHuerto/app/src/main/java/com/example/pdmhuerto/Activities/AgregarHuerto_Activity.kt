package com.example.pdmhuerto.Activities

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
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
        val macetasTierra= getData("Tierra")

        val macetas = listOf("...") + macetasTierra.filter {it.contains("Macetas")}
        val tierra  = listOf("...") + macetasTierra.filter {it.contains("Suelo")}

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
            R.id.datePicker          -> showDate()

            R.id.button_create_event -> {
                if(validateData()) saveEvent()
                else alertDisplayer("Data invalid", "The data input is invalid, please check it and try again")
            }
        }
    }

    private fun validateData(): Boolean{
        if(!::semillaSelected.isInitialized || semillaSelected == "...") return false
        if(!::macetaSelected.isInitialized  || macetaSelected == "...") return false
        if(!::tierraSelected.isInitialized  || tierraSelected == "...") return false
        if(!::dateSelected.isInitialized) return false

        return true
    }

    private fun saveEvent(){
        val userPointer = ParseUser.getCurrentUser().objectId

        val post = ParseObject("Huerto")
        post.put("Semilla", semillaSelected)
        post.put("Maceta", macetaSelected)
        post.put("Tierra", tierraSelected)
        post.put("date", dateSelected)
        post.put("postedBy", ParseObject.createWithoutData("_User", userPointer))

        post.save()
        openActivity()
    }

    private fun openActivity(){
        val intent = Intent(this, Navigation_Activity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showDate(){
        val c = Calendar.getInstance()
        val day    = c.get(Calendar.DAY_OF_MONTH)
        val year   = c.get(Calendar.YEAR)
        val month  = c.get(Calendar.MONTH)

        val datePick = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{ _, year, month, dayOfMonth ->
                val myCal = Calendar.getInstance()
                myCal.set(Calendar.YEAR, year)
                myCal.set(Calendar.MONTH, month)
                myCal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                myCal.set(Calendar.HOUR_OF_DAY, 0)
                myCal.set(Calendar.MINUTE, 0)
                myCal.set(Calendar.SECOND, 0)
                myCal.set(Calendar.MILLISECOND, 0)

                dateSelected = myCal.time
            }, year, month, day)

        datePick.show()
    }

    fun alertDisplayer(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)

        builder.setNegativeButton(android.R.string.no) { _, _->
            Toast.makeText(applicationContext,
                android.R.string.no, Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }
}
