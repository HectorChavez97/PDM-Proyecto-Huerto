package com.example.pdmhuerto.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pdmhuerto.Activities.Semillas_Activity
import com.example.pdmhuerto.R
import com.parse.ParseFile
import com.parse.ParseObject
import org.jetbrains.anko.find


//Adaptador para mostrar todos los elementos de semillas, tierra, macetas y herramientas (utilizan todos el card_view_catalogo)
//para tener el mismo estilo -> Element = semillas o tierra o macetas o herramientas

class ElementsAdapter(val list: List<ParseObject>, val isSemilla: Boolean): RecyclerView.Adapter<ElementsAdapter.ViewHolder>(), View.OnClickListener {
    lateinit var cardView: CardView
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_catalogo, parent, false)

        context = parent.context
        cardView = v.find(R.id.card_view_holder)
        cardView.setOnClickListener(this)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    override fun onClick(v: View?) {
        if(isSemilla){
            when(v?.id){
                R.id.card_view_holder -> {
                    val intent = Intent(context, Semillas_Activity::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val picture: ImageView      = itemView.findViewById(R.id.element_picture)
        private val name: TextView          = itemView.findViewById(R.id.element_name)
        private val description: TextView   = itemView.findViewById(R.id.element_description)

        private var rm = Glide.with(view)

        fun bindItems(data: ParseObject){
            val img: ParseFile = data.getParseFile("foto1")!!           //agregar una para columna nueva para mostrar review

            name.text           = data.getString("nombre")
            description.text    = data.getString("descripcion")
            rm.load(img.data).into(picture)
        }
    }
}


