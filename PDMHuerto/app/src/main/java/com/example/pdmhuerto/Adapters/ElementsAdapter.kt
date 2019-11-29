package com.example.pdmhuerto.Adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pdmhuerto.Interfaces.ElementCardViewListener
import com.example.pdmhuerto.R
import com.parse.ParseFile
import com.parse.ParseObject
import org.jetbrains.anko.find


//Adaptador para mostrar todos los elementos de semillas, tierra, macetas y herramientas (utilizan todos el card_view_catalogo)
//para tener el mismo estilo -> Element = semillas o tierra o macetas o herramientas

class ElementsAdapter(val list: List<ParseObject>, OnCardViewListener: ElementCardViewListener): RecyclerView.Adapter<ElementsAdapter.ViewHolder>() {
    private val onCardViewListener = OnCardViewListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_catalogo, parent, false)

        return ViewHolder(v, onCardViewListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    class ViewHolder(view: View, OnCardViewListener: ElementCardViewListener): RecyclerView.ViewHolder(view), View.OnClickListener{
        private val picture: ImageView      = itemView.find(R.id.element_picture)
        private val name: TextView          = itemView.find(R.id.element_name)
        private val description: TextView   = itemView.find(R.id.element_description)

        private var rm = Glide.with(view)
        private val onCardViewListener = OnCardViewListener

        init{
           view.setOnClickListener(this)
        }

        fun bindItems(data: ParseObject){
            val img: ParseFile = data.getParseFile("fotoPrevia")!!           //agregar una para columna nueva para mostrar review

            name.text           = data.getString("nombre")
            description.text    = data.getString("descripcion")
            rm.load(img.data).into(picture)
        }

        override fun onClick(v: View?) {
            onCardViewListener.onCardViewClick(adapterPosition)
        }
    }
}


